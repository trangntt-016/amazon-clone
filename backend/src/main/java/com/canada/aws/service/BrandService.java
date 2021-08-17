package com.canada.aws.service;

import com.canada.aws.model.Brand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {
    List<Brand>getBrandsByBrandIds(List<Integer>brandids);
}
