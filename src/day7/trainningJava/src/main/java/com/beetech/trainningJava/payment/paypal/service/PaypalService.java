package com.beetech.trainningJava.payment.paypal.service;

import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.model.OrderModel;
import com.beetech.trainningJava.service.IAccountService;
import com.beetech.trainningJava.utils.Utils;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class chứa các method xử lý logic của paypal
 */
@Service
public class PaypalService {
    private static final String currency = "USD";

    @Autowired
    private IAccountService accountService;

    // Đối tượng apiContext sẽ được sử dụng để gọi các API của PayPal
    @Autowired
    APIContext apiContext;

    // Đối tượng redirectUrls sẽ chứa các url của trang web
    @Autowired
    RedirectUrls redirectUrls;

    /**
     * Xác thực thông tin của 1 payment và trả về link redirect để chuyển hướng đến trang paypal
     *
     * @param orderModel là thông tin của order cần tạo payment
     * @return link redirect để chuyển hướng đến trang paypal
     */
    public String authorizePayment(OrderModel orderModel) {
        // Khởi tạo đối tượng payer, redirectUrls, listTransaction cho việc tạo payment
        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = this.redirectUrls;
        List<Transaction> listTransaction = getTransactionInformation(orderModel);

        // Khởi tạo đối tượng request payment
        Payment requestPayment = new Payment();
        requestPayment.setIntent("authorize")
                .setPayer(payer)
                .setRedirectUrls(redirectUrls)
                .setTransactions(listTransaction);

        // Thực hiện goi API để tạo 1 approved payment
        Payment approvedPayment = null;
        try {
            approvedPayment = requestPayment.create(apiContext);
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }

        // Sử dụng approved payment để lấy link redirect
        assert approvedPayment != null;
        return getApprovalLink(approvedPayment);
    }

    /**
     * Lấy thông tin của người thanh toán
     *
     * @return đối tượng payer chứa thông tin của người thanh toán
     */
    private Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        PayerInfo payerInfo = new PayerInfo();
        if (accountService.isLogin()) {
            AccountEntity accountEntity = accountService.getAccountEntity();
            payerInfo.setFirstName(accountEntity.getName())
                    .setEmail(accountEntity.getEmail());
        } else {
            payerInfo.setFirstName("No name")
                    .setEmail("No email");
        }
        payer.setPayerInfo(payerInfo);
        return payer;
    }

    /**
     * Lấy thông tin của transaction
     *
     * @param orderModel là thông tin của order cần tạo payment
     * @return danh sách các transaction chứa thông tin của order và các phí liên quan
     */
    private List<Transaction> getTransactionInformation(OrderModel orderModel) {
        // khởi tạo đối tượng detail chứa thông tin về các phí liên quan
        final String[] deviation = {Utils.ChangeVndToUsd(orderModel.getTotalAmount().toString())};
        Details details = new Details();
        // Thiết lập phí tổng các sản phẩm
        details.setSubtotal(Utils.ChangeVndToUsd(orderModel.getTotalAmount().toString()));

        // Khởi tạo đối tượng amount chứa thông tin về tổng tiền và đơn vị tiền tệ
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setDetails(details);
        amount.setTotal(Utils.ChangeVndToUsd(orderModel.getTotalAmount().toString()));

        // Khởi tạo đối tượng transaction
        // Chứa thông tin về mô tả, tổng tiền, số hóa đơn, số tiền tương ứng với từng sản phẩm
        Transaction transaction = new Transaction();
        transaction.setDescription("Payment description");
        transaction.setAmount(amount);
        transaction.setInvoiceNumber(orderModel.getId().toString());

        // Khởi tạo danh sách các item chứa thông tin về các sản phẩm
        List<Item> itemList = new ArrayList<>();
        orderModel.getCartProductInforModelList().forEach(cartProductInforModel -> {
            // Khởi tạo đối tượng item chứa thông tin về 1 sản phẩm
            Item item = new Item();
            item.setName(cartProductInforModel.getProduct().getName())
                    .setCurrency(currency)
                    .setPrice(Utils.ChangeVndToUsd(cartProductInforModel.getProduct().getPrice().toString()))
                    .setQuantity(cartProductInforModel.getQuantity().toString());
            // Thực hiện tính chênh lệch khi chuyển đổi từ VND sang USD
            deviation[0] = (Utils.checkRoundDirection(
                    BigDecimal.valueOf(Double.parseDouble(deviation[0]) -
                            Double.parseDouble(item.getPrice()) * Integer.parseInt(item.getQuantity())
                    )).toString());
            itemList.add(item);
        });

        // Thêm item chứa thông tin về phí giảm giá
        Item item = new Item();
        item.setName("Discount")
                .setCurrency(currency)
                .setPrice("-" + Utils.ChangeVndToUsd(orderModel.getTotalDiscount().toString()))
                .setQuantity("1");
        itemList.add(item);
        // Thực hiện tính chênh lệch khi chuyển đổi từ VND sang USD
        deviation[0] = (Utils.checkRoundDirection(BigDecimal.valueOf(Double.parseDouble(deviation[0]) -
                Double.parseDouble(item.getPrice()) * Integer.parseInt(item.getQuantity()))).toString());

        // Thêm item chứa thông tin về phí chênh lệch
        Item itemTmp = new Item();
        itemTmp.setName("Deviation")
                .setCurrency(currency)
                .setPrice(deviation[0])
                .setQuantity("1");
        itemList.add(itemTmp);

        // Thêm danh sách các item vào transaction
        transaction.setItemList(new ItemList().setItems(itemList));

        // Trả về danh sách các transaction
        return new ArrayList<>(List.of(transaction));
    }

    /**
     * Lấy link redirect
     * @param approvedPayment là đối tượng payment đã được approved
     * @return link redirect
     */
    private String getApprovalLink(Payment approvedPayment) {
        // Lấy danh sách các link trong approved payment
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            // Lấy link redirect
            if (link.getRel().equalsIgnoreCase("approval_url")) { // Bằng cách kiểm tra rel có bằng approval_url hay không
                approvalLink = link.getHref();
                System.out.println("approvalLink: " + approvalLink);
                break;
            }
        }
        // Trả về link redirect
        return approvalLink;
    }

    /**
     * Lấy thông tin của payment
     * @param paymentId là id của payment
     * @return đối tượng payment chứa thông tin thanh toán
     * @throws PayPalRESTException ném ra lỗi khi get thông tin payment
     */
    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        return Payment.get(apiContext, paymentId);
    }

    /**
     * Thực hiện thanh toán
     * @param paymentId là id của payment
     * @param payerId là id của người thanh toán
     * @return đối tượng payment chứa thông tin thanh toán
     */
    public Payment executePayment(String paymentId, String payerId) {
        // Khởi tạo đối tượng paymentExecution chứa thông tin của người thanh toán
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        // Khởi tạo đối tượng payment với id của payment
        Payment payment = new Payment().setId(paymentId);

        // Thực hiện thanh toán
        try {
            return payment.execute(apiContext, paymentExecution);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payment;
    }
}
