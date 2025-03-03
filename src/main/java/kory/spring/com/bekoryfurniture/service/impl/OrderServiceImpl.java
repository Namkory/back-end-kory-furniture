package kory.spring.com.bekoryfurniture.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kory.spring.com.bekoryfurniture.dto.OrderDTO;
import kory.spring.com.bekoryfurniture.entity.Customer;
import kory.spring.com.bekoryfurniture.entity.OrderDetail;
import kory.spring.com.bekoryfurniture.entity.Orders;
import kory.spring.com.bekoryfurniture.entity.Products;
import kory.spring.com.bekoryfurniture.exception.AppException;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.repository.CustomerRepo;
import kory.spring.com.bekoryfurniture.repository.OrderDetailRepo;
import kory.spring.com.bekoryfurniture.repository.OrderRepo;
import kory.spring.com.bekoryfurniture.repository.ProductRepo;
import kory.spring.com.bekoryfurniture.service.OrderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kory.spring.com.bekoryfurniture.utils.DateTimeUtils.getCurrentDate;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private ModelMapper modelMapper;
    private OrderRepo orderRepo;
    private ProductRepo productRepo;
    private CustomerRepo customerRepo;
    private OrderDetailRepo orderDetailRepo;
    private JavaMailSender mailSender;

    @Override
    @Transactional
    public OrderDTO createNewOrder(OrderDTO request) throws MessagingException {
        if (request.getCustomerId() == 0 || request.getProductsId() == null){
            throw  new AppException(ErrorCode.INVALID_CUSTOMER_ID_OR_LIST_PRODUCT_ID);
        }
        Customer customer = customerRepo.findById(request.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_CUSTOMER));
        customer.setLastPurchaseDate(getCurrentDate());
        customerRepo.save(customer);

        Orders orderEntity = new Orders();
        modelMapper.map(request, orderEntity);
        orderEntity.setPaymentId(request.getPaymentId());
        orderEntity.setStatus("Successful");
        orderEntity.setOrderDate(getCurrentDate());
        orderEntity.setCustomerName(customer.getName());
        List<OrderDetail> orderDetailsList = new ArrayList<>();
        for (int productId : request.getProductsId()) {
            Products product = productRepo.findById(productId)
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_PRODUCT));
            if(product.getAmount() <= 0){
                throw new AppException(ErrorCode.OUT_OF_STOCK, "Product named: " + product.getName() + " is out of stock");
            }
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProductName(product.getName());
            orderDetail.setPrice(product.getSalePrice());
            orderDetail.setImage(product.getThumbnailData()[0]);
            orderDetail.setOrder(orderEntity);
            orderDetail.setCreatedAt(getCurrentDate());
            orderDetailsList.add(orderDetail);
            orderDetailRepo.save(orderDetail);
            int remainingQuantity = product.getAmount() - 1;
            product.setAmount(remainingQuantity);
            productRepo.save(product);
        }
        orderEntity.setListOrderDetail(orderDetailsList);
        orderRepo.save(orderEntity);

        //send gratitude email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("nguyenhoainamkory@gmail.com");
        helper.setTo(customer.getEmail());
        helper.setSubject("Thanks");
        helper.setText("Kory Furniture xin chân thành cảm ơn Quý Khách " +
                "đã tin tưởng và lựa chọn sản phẩm của chúng tôi. Chúng tôi rất" +
                " trân trọng sự ủng hộ của Quý Khách và hy vọng rằng sản phẩm" +
                " sẽ mang đến sự hài lòng và tiện nghi cho không gian của bạn.", true);

        mailSender.send(message);

        OrderDTO res = modelMapper.map(orderEntity, OrderDTO.class);
        res.setListOrderDetail(orderEntity.getListOrderDetail());
        return res;
    }

    @Override
    @Transactional
    public Page<OrderDTO> getAllOrder(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Orders> adminsPage = orderRepo.findAll(pageable);
        Page<OrderDTO> orderResponsesPage = adminsPage.map(orderEntity -> {
            OrderDTO orderResponse = modelMapper.map(orderEntity, OrderDTO.class);
            return orderResponse;
        });

        return orderResponsesPage;
    }

    @Override
    @Transactional
    public OrderDTO getOrderById(Integer orderId) {
        Orders orderEntity = orderRepo.findById(orderId).orElseThrow(() ->
                new AppException(ErrorCode.NOT_FOUND_ORDER));

        return modelMapper.map(orderEntity, OrderDTO.class);
    }
}
