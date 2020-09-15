package ru.erasko.rest;

import ru.erasko.service.repr.CategoryRepr;
import ru.erasko.service.repr.ProductRepr;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Local
@Path("/product")
public interface ProductServiceRs {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void insert(ProductRepr productRepr);

    @POST
    @Path("/category")
    @Consumes(MediaType.APPLICATION_JSON)
    void insertCategoryRs(CategoryRepr categoryRepr);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    void update(ProductRepr productRepr);

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void delete(@PathParam("id") Long id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<ProductRepr> findAll();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    ProductRepr findByIdRs(@PathParam("id") Long id);

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    ProductRepr findByNameRs(@PathParam("name") String name);

    @GET
    @Path("/category/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    List<ProductRepr> findByCategoryIdRs(@PathParam("id") Long id);

}
