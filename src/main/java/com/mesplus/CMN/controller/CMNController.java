package com.mesplus.CMN.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mesplus.CMN.dao.MenuDao;
import com.mesplus.CMN.model.Menu;
import com.mesplus.smartfactory.HomeController;

@Controller
public class CMNController {
	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);
	
	private MenuDao menuDao;
	
	@Autowired
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@RequestMapping(value = "module/CMN/data/menus.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Menu> menus(HttpServletRequest request,
			HttpServletResponse response) {
		String factory_id = request.getParameter("factory_id");

		logger.info("factory_id : " + factory_id);
		
		return Menu.buildHierarchy(menuDao.selectMenus());
	}

}