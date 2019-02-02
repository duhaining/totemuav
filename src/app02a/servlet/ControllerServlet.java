package app02a.servlet;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app02a.domain.Product;
import app02a.form.ProductForm;


/**
 * 这里遵从了一个约定：所有Servlet的类名称都带有servlet后缀。
 */
@WebServlet(name = "ControllerServlet", urlPatterns = {"/product_input.action",
    "/product_save.action"})
public class ControllerServlet extends HttpServlet
{

    private static final long serialVersionUID = 9111454017486497956L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String uri = request.getRequestURI();
        int lastIndex = uri.lastIndexOf("/");
        String action = uri.substring(lastIndex + 1);
        if ("product_input.action".equals(action))
        {
            // no action class,there is nothing to be done
        }
        else if ("product_save.action".equals(action))
        {
            // create form
            ProductForm productForm = new ProductForm();
            productForm.setName(request.getParameter("name"));
            productForm.setDescription(request.getParameter("description"));
            productForm.setPrice(request.getParameter("price"));
            // create model
            Product product = new Product();
            product.setName(productForm.getName());
            product.setDescription(productForm.getDescription());
            try
            {
                product.setPrice(Float.parseFloat(productForm.getPrice()));
            }
            catch (NumberFormatException e)
            {}
            // code to save product
            // store model in a scope variable for the view
            request.setAttribute("product", product);
        }
        String dispatchUrl = null;
        if ("product_input.action".equals(action))
        {
            dispatchUrl = "/WEB-INF/jsp/ProductForm.jsp";
        }
        else if ("product_save.action".equals(action))
        {
            dispatchUrl = "/WEB-INF/jsp/ProductDetails.jsp";
        }
        if (dispatchUrl != null)
        {
            RequestDispatcher rd = request.getRequestDispatcher(dispatchUrl);
            rd.forward(request, response);
        }
    }
}
