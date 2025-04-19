package com.club.badminton.controller.web;

import org.springframework.ui.Model;

import static com.club.badminton.init.InitApp.ADDRESSES;

public class AddressUtil {

    public static void addAddressAttributes(Model model) {
        model.addAttribute("addressLv1List", ADDRESSES.getLv1List());
        model.addAttribute("addressChildrenMap", ADDRESSES.getChildrenMap());
    }

}
