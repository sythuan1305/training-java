package com.beetech.trainningJava.aspect.interfaces.imp;

import com.beetech.trainningJava.aspect.interfaces.IIntroduction;
import org.springframework.stereotype.Component;

@Component
public class IntroductionImp implements IIntroduction {
    @Override
    public String introduction() {
        return "IntroductionImp";
    }
}
