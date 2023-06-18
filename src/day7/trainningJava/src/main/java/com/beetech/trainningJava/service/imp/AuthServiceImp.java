package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.entity.CartEntity;
import com.beetech.trainningJava.entity.RefreshTokenEntity;
import com.beetech.trainningJava.enums.Role;
import com.beetech.trainningJava.exception.HttpError;
import com.beetech.trainningJava.jwt.JwtTokenProvider;
import com.beetech.trainningJava.model.AccountRegisterDto;
import com.beetech.trainningJava.model.JwtTokenModel;
import com.beetech.trainningJava.repository.AccountRepository;
import com.beetech.trainningJava.repository.RefreshTokenRepository;
import com.beetech.trainningJava.service.IAccountService;
import com.beetech.trainningJava.service.IAuthService;
import com.beetech.trainningJava.service.ICartProductService;
import com.beetech.trainningJava.utils.Utils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Class này dùng để implement interface IAuthService
 *
 * @see IAuthService
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthServiceImp implements IAuthService {
    private final AuthenticationManager authManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final AccountRepository accountRepository;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    private final ICartProductService cartProductService;

    private final IAccountService accountService;

    @Override
    public JwtTokenModel getJwtTokenModelAfterAuthen(String username, String password, String cartCookie, HttpServletResponse response) {
        // Xác thực từ username và password.
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Và trả về jwt token cho người dùng.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(username);
        addRefreshTokenCookie(username, jwt, response);
        saveCartFromCookieToDB(cartCookie, response);
        return new JwtTokenModel(jwt, jwtTokenProvider.getExpiredTime());
    }

    @Override
    public void addRefreshTokenCookie(String username, String accessToken, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.createRefreshToken(username, accessToken);

        Cookie cookie = new Cookie(Utils.REFRESH_TOKEN_COOKIE_NAME, refreshToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(Utils.REFRESH_TOKEN_COOKIE_LENGTH); // ms -> s
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public JwtTokenModel reCreateJwtTokenModel(String refreshToken, String accessToken, HttpServletResponse response) {
        String username = jwtTokenProvider.getUsernameFromJwt(accessToken);
        System.out.println("username: " + username);

        if (jwtTokenProvider.isValidToCreateNewAccessToken(username, refreshToken, accessToken)) {
            String newAccessToken = jwtTokenProvider.generateToken(username);
            addRefreshTokenCookie(username, newAccessToken, response);

            return new JwtTokenModel(newAccessToken, Utils.REFRESH_TOKEN_COOKIE_LENGTH);
        } else {
            throw new HttpError("Unable to create new access token", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void register(AccountRegisterDto accountRegisterDto) {
        String registerEmail = accountRegisterDto.getEmail();

        // check email exist
        Optional<AccountEntity> existAcc = accountRepository.findByEmail(registerEmail);
        // if email is already registered
        if (existAcc.isPresent()) {

            // if email is activated
            if (existAcc.get().isVerify()) {
                throw new HttpError("Email already existed: " + registerEmail
                        , HttpStatus.CONFLICT);

            } else {
                // if email isn't activated yet then re-send the activation link
                emailService.sendRegisterTokenEmail(existAcc.get(), registerEmail);

                // send back an error to warning client
                throw new HttpError("Please check your email to verify your account!"
                        , HttpStatus.LOCKED);
            }
        }

        // if email is not registered yet
        // create new account
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                .username(accountRegisterDto.getUsername())
                .refreshToken("")
                .accessToken("")
                .build();
        CartEntity cartEntity = CartEntity.builder()
                .totalPrice(BigDecimal.ZERO)
                .totalQuantity(0)
                .build();
        AccountEntity newAcc = AccountEntity.builder()
                .email(registerEmail)
                .password(passwordEncoder.encode(accountRegisterDto.getPassword()))
                .refreshTokenEntity(refreshTokenEntity)
                .cart(cartEntity)
                .role(Role.USER)
                .token(jwtTokenProvider.generateSpecificToken("Verify " + accountRegisterDto.getEmail(), Utils.VERIFY_TOKEN_EXPIRED_TIME))
                .build();
        accountRepository.save(newAcc);
        emailService.sendRegisterTokenEmail(newAcc, registerEmail);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void verifyAccount(String token) {
        if (jwtTokenProvider.isValidToken(token)) {
            String email = jwtTokenProvider.getUsernameFromJwt(token);
            if (email.startsWith("Verify ")) {
                email = email.substring(7);
            } else {
                throw new HttpError("Token is not a verify token", HttpStatus.UNAUTHORIZED);
            }
            AccountEntity accountEntity = accountRepository
                    .findByEmail(email)
                    .orElseThrow(() -> new HttpError("Token not found", HttpStatus.NOT_FOUND));


            if (accountEntity.isVerify()) {
                throw new HttpError("Account is already verified", HttpStatus.CONFLICT);
            }
            accountEntity.setVerify(true);
            accountEntity.setToken("");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendPasswordResetToken(String email) {
        AccountEntity accountEntity = accountRepository
                .findByEmail(email)
                .orElseThrow(() -> new HttpError("Email not found", HttpStatus.NOT_FOUND));
        if (!accountEntity.isVerify()) {
            throw new HttpError("Account is not verified", HttpStatus.CONFLICT);
        }
        accountEntity.setToken(jwtTokenProvider
                .generateSpecificToken(
                        "ResetPw " + accountEntity.getEmail(),
                        Utils.VERIFY_TOKEN_EXPIRED_TIME));
        emailService.sendForgotPasswordEmail(accountEntity, email);
    }

    @Override
    public void validateResetPasswordToken(String token) {
        if (jwtTokenProvider.isValidToken(token)) {
            String email = jwtTokenProvider.getUsernameFromJwt(token);
            if (email.startsWith("ResetPw ")) {
                email = email.substring(8);
            } else {
                throw new HttpError("Token is not a reset password token", HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void resetPassword(String token, String newPassword) {
        validateResetPasswordToken(token);
        AccountEntity accountEntity = accountRepository
                .findByToken(token)
                .orElseThrow(() -> new HttpError("Token not found", HttpStatus.NOT_FOUND));
        accountEntity.setPassword(passwordEncoder.encode(newPassword));
    }

    @Override
    public void saveCartFromCookieToDB(String cartCookie, HttpServletResponse response) {
        // Lưu giỏ hàng vào database nếu đã đăng nhập và cookie cart khác giá trị mặc định
        if (!Utils.DEFAULT_COOKIE_VALUE.equals(cartCookie)) {
            cartProductService.saveCartProductEntityListWithAuthenticatedByCartProductParserList(
                    Utils.JsonParserListObjectWithEncodedURL(cartCookie));
            // Xóa cookie
//            Utils.deleteCookie("cart", response);
        }
    }
}
