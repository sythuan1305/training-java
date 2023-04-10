package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.DiscountEntity;
import com.beetech.trainningJava.repository.DiscountRepository;
import com.beetech.trainningJava.service.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImp implements IDiscountService {
    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public List<DiscountEntity> getAll() {
        return discountRepository.findAll();
    }

    @Override
    public DiscountEntity getOne(Integer id) {
        return discountRepository.getReferenceById(id);
    }

}
