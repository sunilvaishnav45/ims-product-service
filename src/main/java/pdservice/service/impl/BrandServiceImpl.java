package pdservice.service.impl;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pdservice.dao.BrandDao;
import pdservice.dao.custom.*;
import pdservice.entity.Brand;
import pdservice.service.BrandService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandCustomDao brandCustomDao;

    @Autowired
    private BrandDao brandDao;

    private static final Logger LOGGER = Logger.getLogger(BrandServiceImpl.class);

    @Override
    public boolean brandExists(String brandName) {
        return brandCustomDao.getBrandByName(brandName).isPresent();
    }

    @Override
    public boolean brandExists(Brand brand) {
        return brandDao.existsById(brand.getId());
    }

    @Override
    public Optional<Brand> unAvailableBrand(Brand brand) {
        return brandCustomDao.unAvailableBrand(brand);
    }

    @Override
    public Optional<List<Brand>> findByIds(List<Integer> ids) {
        return brandCustomDao.findByIds(ids);
    }

    @Override
    public Optional<List<Brand>> findAll() {
        return brandCustomDao.findAll();
    }

    @Override
    public Optional<Brand> updateBrand(Brand brand) {
        return brandCustomDao.updateBrand(brand);
    }
}
