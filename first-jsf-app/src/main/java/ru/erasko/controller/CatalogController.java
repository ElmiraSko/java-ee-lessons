package ru.erasko.controller;

import ru.erasko.persist.CatalogRepository;
import ru.erasko.persist.entity.Catalog;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@SessionScoped
@Named
public class CatalogController implements Serializable {

    @Inject
    private CatalogRepository catalogRepository;
    private Catalog catalog;

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public List<Catalog> getAllCatalogs() throws SQLException {
        return catalogRepository.findAll();
    }

    public String createCatalog() {
        this.catalog = new Catalog();
        return "/catalog-form.xhtml?faces-redirect=true";
    }

    public String editCatalog(Catalog catalog) {
        this.catalog = catalog;
        return "/catalog-form.xhtml?faces-redirect=true";
    }

    public void deleteCatalog(Catalog catalog) throws SQLException {
        catalogRepository.delete(catalog.getId());
    }

    public String saveCatalog() throws SQLException {
        if (catalog.getId() != null) {
            catalogRepository.update(catalog);
        } else {
            catalogRepository.insert(catalog);
        }
        return "/catalogs.xhtml?faces-redirect=true";
    }
}
