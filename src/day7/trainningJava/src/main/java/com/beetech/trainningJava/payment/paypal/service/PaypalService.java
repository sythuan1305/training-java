package com.beetech.trainningJava.payment.paypal.service;

import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.model.OrderModel;
import com.beetech.trainningJava.service.IAccountService;
import com.beetech.trainningJava.utils.Utils;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaypalService {
    @Autowired
    private IAccountService accountService;

    @Autowired
    APIContext apiContext;

    @Autowired
    RedirectUrls redirectUrls;

    private static final String currency = "USD";

    public String authorizePayment(OrderModel orderModel) {
        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = this.redirectUrls;
        List<Transaction> listTransaction = getTransactionInformation(orderModel);

        Payment requestPayment = new Payment();
        requestPayment.setIntent("authorize")
                .setPayer(payer)
                .setRedirectUrls(redirectUrls)
                .setTransactions(listTransaction);
        Payment approvedPayment = null;
        try {
            approvedPayment = requestPayment.create(apiContext);
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        assert approvedPayment != null;
        return getApprovalLink(approvedPayment);
    }

    private Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        PayerInfo payerInfo = new PayerInfo();
        if (accountService.isLogin()) {
            AccountEntity accountEntity = accountService.getAccountEntity();
            payerInfo.setFirstName(accountEntity.getName())
                    .setEmail(accountEntity.getEmail());
        } else {
            payerInfo.setFirstName("No name")
                    .setEmail("No email");
        }
        payer.setPayerInfo(payerInfo);
        return payer;
    }

    private List<Transaction> getTransactionInformation(OrderModel orderModel) {
        final String[] deviation = {Utils.ChangeVndToUsd(orderModel.getTotalAmount().toString())};
        Details details = new Details();
        details.setSubtotal(Utils.ChangeVndToUsd(orderModel.getTotalAmount().toString()));

        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setDetails(details);
        amount.setTotal(Utils.ChangeVndToUsd(orderModel.getTotalAmount().toString()));

        Transaction transaction = new Transaction();
        transaction.setDescription("Payment description");
        transaction.setAmount(amount);
        transaction.setInvoiceNumber(orderModel.getId().toString());

        List<Item> itemList = new ArrayList<>();
        orderModel.getCartProductInforModelList().forEach(cartProductInforModel -> {
            Item item = new Item();
            item.setName(cartProductInforModel.getProduct().getName())
                    .setCurrency(currency)
                    .setPrice(Utils.ChangeVndToUsd(cartProductInforModel.getProduct().getPrice().toString()))
                    .setQuantity(cartProductInforModel.getQuantity().toString());
        deviation[0] = (Utils.checkRoundDirection(BigDecimal.valueOf(Double.parseDouble(deviation[0]) -
                Double.parseDouble(item.getPrice()) * Integer.parseInt(item.getQuantity())
        )).toString());
            itemList.add(item);
        });

        Item item = new Item();
        item.setName("Discount")
                .setCurrency(currency)
                .setPrice("-" + Utils.ChangeVndToUsd(orderModel.getTotalDiscount().toString()))
                .setQuantity("1");
        itemList.add(item);
        deviation[0] = (Utils.checkRoundDirection(BigDecimal.valueOf(Double.parseDouble(deviation[0]) -
                Double.parseDouble(item.getPrice()) * Integer.parseInt(item.getQuantity()))).toString());

        if (deviation[0].compareTo("0.00") > 0) {
            Item itemTmp = new Item();
            itemTmp.setName("Deviation")
                    .setCurrency(currency)
                    .setPrice(deviation[0])
                    .setQuantity("1");
            itemList.add(itemTmp);
        } else if (deviation[0].compareTo("0.00") < 0) {
            Item itemTmp = new Item();
            itemTmp.setName("Deviation")
                    .setCurrency(currency)
                    .setPrice(deviation[0])
                    .setQuantity("1");
            itemList.add(itemTmp);
        }

        transaction.setItemList(new ItemList().setItems(itemList));
        return new ArrayList<>(List.of(transaction));
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                System.out.println("approvalLink: " + approvalLink);
                break;
            }
        }

        return approvalLink;
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        return Payment.get(apiContext, paymentId);
    }

    public Payment executePayment(String paymentId, String payerId) {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment().setId(paymentId);

        try {
            return payment.execute(apiContext, paymentExecution);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payment;
    }
}
