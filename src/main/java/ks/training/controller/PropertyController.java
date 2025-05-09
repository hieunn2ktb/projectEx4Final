package ks.training.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ks.training.dao.PropertyDao;
import ks.training.dto.PropertyDto;
import ks.training.dto.PropertyResponse;
import ks.training.entity.Property;
import ks.training.entity.User;
import ks.training.service.CustomerActivityService;
import ks.training.service.PropertyService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet("/propertyMng")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class PropertyController extends HttpServlet {
    private PropertyService propertyService;
    private CustomerActivityService customerActivityService;
    public PropertyController() {
        this.propertyService = new PropertyService();
        this.customerActivityService = new CustomerActivityService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tham số action");
            return;
        }
        switch (action) {
            case "delete":
                deleteProperty(req, resp);
                break;
            case "viewAddProperty":
                showViewAddProperty(req, resp);
                break;
            case "addProperty":
                addProperty(req, resp);
                break;
            case "edit":
                editProperty(req, resp);
                break;
            case "editSuccess":
                updateProperty(req, resp);
                break;
            case "info":
                getProperty(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Hành động không hợp lệ");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void deleteProperty(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
        int propertyId = Integer.parseInt(request.getParameter("id"));
        int createBy = Integer.parseInt(request.getParameter("createBy"));
        String msgDelete = "";
        HttpSession session = request.getSession();
        if (propertyService.checkUser(propertyId, createBy)) {
            msgDelete = "Bất động sản không thể xoá, vì bạn không phải là người tạo";
        } else if (propertyService.checkTransactionSQL(propertyId)) {
            msgDelete = "Bất động sản đã có giao dịch, không thể xoá";
        } else {
            int rowsAffected = propertyService.deleteProperty(propertyId);
            msgDelete = (rowsAffected > 0) ? "Đã xoá bất động sản" : "Xoá thất bại";
        }
        session.setAttribute("deleteMessage", msgDelete);
        getAllList(request);
        request.getRequestDispatcher("index.jsp").forward(request, resp);
    }

    private void showViewAddProperty(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("User") : null;

        if (user == null) {
            resp.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }
        request.getRequestDispatcher("/property/addProperty.jsp").forward(request, resp);
    }

    private void addProperty(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("User") : null;

        if (user == null) {
            resp.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }

        if (!request.getContentType().toLowerCase().startsWith("multipart/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request không hỗ trợ multipart");
            return;
        }

        try {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String address = request.getParameter("address");
            String propertyType = request.getParameter("property_type");
            double price = Double.parseDouble(request.getParameter("price"));
            double acreage = Double.parseDouble(request.getParameter("acreage"));

            Collection<Part> fileParts = request.getParts();
            List<InputStream> imageStreams = new ArrayList<>();

            for (Part part : fileParts) {
                if ("images".equals(part.getName()) && part.getSize() > 0) {
                    imageStreams.add(part.getInputStream());
                }
            }

            PropertyResponse propertyResponse = new PropertyResponse();
            propertyResponse.setTitle(title);
            propertyResponse.setDescription(description);
            propertyResponse.setAddress(address);
            propertyResponse.setPrice(price);
            propertyResponse.setPropertyType(propertyType);
            propertyResponse.setAcreage(acreage);
            propertyResponse.setImageStreams(imageStreams);
            propertyResponse.setCreatedBy(user.getId());

            String uploadRootPath = getServletContext().getRealPath("") + File.separator + "uploads";
            int propertyId = propertyService.addProperty(propertyResponse, uploadRootPath);

            String msg = (propertyId != -1) ? "Thêm thành công bất động sản" : "Thêm thất bại";
            request.setAttribute("msg", msg);

            RequestDispatcher rd = request.getRequestDispatcher("/property/addProperty.jsp");
            rd.forward(request, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý yêu cầu!");
        }
    }


    private void editProperty(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("User") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        User user = (User) session.getAttribute("User");

        int propertyId = Integer.parseInt(request.getParameter("propertyId"));
        PropertyDao propertyDao = new PropertyDao();
        Property property = propertyDao.findPropertyById(propertyId);
        List<String> images = propertyDao.getImagesByPropertyId(propertyId);

        request.setAttribute("user", user);
        request.setAttribute("property", property);
        request.setAttribute("images", images);
        request.getRequestDispatcher("/property/editProperty.jsp").forward(request, response);
    }
    private void updateProperty(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            HttpSession session = request.getSession(false);
            User user = (session != null) ? (User) session.getAttribute("User") : null;

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/user/login.jsp");
                return;
            }

            if (!request.getContentType().toLowerCase().startsWith("multipart/")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request không hỗ trợ multipart");
                return;
            }

            int propertyId = Integer.parseInt(request.getParameter("propertyId"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String address = request.getParameter("address");
            String propertyType = request.getParameter("property_type");
            double price = Double.parseDouble(request.getParameter("price"));
            double acreage = Double.parseDouble(request.getParameter("acreage"));

            Collection<Part> fileParts = request.getParts();
            List<InputStream> imageStreams = new ArrayList<>();

            for (Part part : fileParts) {
                if ("images".equals(part.getName()) && part.getSize() > 0) {
                    imageStreams.add(part.getInputStream());
                }
            }

            PropertyResponse propertyResponse = new PropertyResponse();
            propertyResponse.setId(propertyId);
            propertyResponse.setTitle(title);
            propertyResponse.setDescription(description);
            propertyResponse.setAddress(address);
            propertyResponse.setPrice(price);
            propertyResponse.setPropertyType(propertyType);
            propertyResponse.setAcreage(acreage);
            propertyResponse.setImageStreams(imageStreams);

            String uploadRootPath = getServletContext().getRealPath("") + File.separator + "uploads";
            propertyService.setUploadRootPath(uploadRootPath);

            boolean isSuccess = propertyService.updateProperty(propertyResponse);
            String msg = isSuccess ? "Cập nhật thành công bất động sản" : "Cập nhật thất bại";

            request.setAttribute("msg", msg);
            editProperty(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý yêu cầu!");
        }
    }


    private void getProperty(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("User") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }

        String propertyIdStr = request.getParameter("propertyId");
        int propertyId = 0;
        try {
            if (propertyIdStr != null) {
                propertyId = Integer.parseInt(propertyIdStr);
            }
        } catch (NumberFormatException e) {
            return;
        }

        Property property = propertyService.findPropertyById(propertyId);
        PropertyDao propertyDao = new PropertyDao();
        List<String> images = propertyDao.getImagesByPropertyId(propertyId);

        customerActivityService.logCustomerActivity(user.getId(), propertyId);

        request.setAttribute("property", property);
        request.setAttribute("images", images);
        request.setAttribute("user", user);

        request.getRequestDispatcher("/property/PropertyInfo.jsp").forward(request, response);
    }


    private void getAllList(HttpServletRequest request) throws ServletException {
        int recordsPerPage = 5;
        int currentPage = 1;
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String searchAddress = request.getParameter("searchAddress");
        String searchPropertyType = request.getParameter("searchPropertyType");
        String pageParam = request.getParameter("page");

        if (pageParam != null && pageParam.matches("\\d+")) {
            currentPage = Integer.parseInt(pageParam);
            if (currentPage < 1) {
                currentPage = 1;
            }
        }
        try {
            int totalRecords = propertyService.countProperties(minPrice, maxPrice, searchAddress, searchPropertyType);
            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

            if (totalPages == 0) {
                totalPages = 1;
            }
            if (currentPage > totalPages) {
                currentPage = totalPages;
            }
            List<PropertyDto> properties = propertyService.findPropertiesByPage(minPrice, maxPrice, searchAddress, searchPropertyType, currentPage, recordsPerPage);
            request.setAttribute("properties", properties);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("searchAddress", searchAddress);
            request.setAttribute("searchPropertyType", searchPropertyType);
            request.setAttribute("minPrice", minPrice);
            request.setAttribute("maxPrice", maxPrice);

        } catch (SQLException e) {
            throw new ServletException("Lỗi truy vấn dữ liệu");
        }
    }



}
