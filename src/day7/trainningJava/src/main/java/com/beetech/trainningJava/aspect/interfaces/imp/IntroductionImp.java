package com.beetech.trainningJava.aspect.interfaces.imp;

import com.beetech.trainningJava.aspect.interfaces.IIntroduction;
import org.springframework.stereotype.Component;

/**
 * Class này dùng để test introductions và implement interface IIntroduction
 * @see IIntroduction
 */
@Component
public class IntroductionImp implements IIntroduction {
    @Override
    public String introduction() {
        return "IntroductionImp";
    }
}
