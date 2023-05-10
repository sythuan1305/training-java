package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.DiscountEntity;
import com.beetech.trainningJava.repository.DiscountRepository;
import com.beetech.trainningJava.service.IDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class này dùng để triển khai các phương thức của interface IDiscountService
 * 
 * @see IDiscountService
 */
@Service
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
public class DiscountServiceImp implements IDiscountService {
    private final DiscountRepository discountRepository;

    @Override
    public List<DiscountEntity> getDistcountEntityList() {
        return discountRepository.findAll();
    }

    @Override
    public DiscountEntity getDiscountEntity(Integer id) {
        return discountRepository.getReferenceById(id);
    }
}
