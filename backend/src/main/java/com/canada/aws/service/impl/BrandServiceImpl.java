package com.canada.aws.service.impl;

import com.canada.aws.model.Brand;
import com.canada.aws.repo.BrandRepository;
import com.canada.aws.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> getBrandsByBrandIds(List<Integer> brandIds) {
        List<Brand>brands = new ArrayList<>();

        brandIds.forEach(id -> {
            Optional<Brand> found = brandRepository.findById(id);
            if(found.isPresent()){
                brands.add(found.get());
            }
        });
        Collections.sort(brands, new SortBrandByName());

        return brands;
    }

}

class SortBrandByName implements Comparator<Brand>{
    @Override
    public int compare(Brand o1, Brand o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
