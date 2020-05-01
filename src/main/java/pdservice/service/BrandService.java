package pdservice.service;

import pdservice.entity.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {

    public boolean brandExists(String brandName);

    public boolean brandExists(Brand brand);

    public Optional<Brand> unAvailableBrand(Brand brand);

    public Optional<List<Brand>> findByIds(List<Integer> ids);

    public Optional<List<Brand>> findAll();

}
