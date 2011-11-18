package com.mesplus.MBI.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mesplus.MBI.dao.FormDao;

@Controller
public class MBIController {

	@Autowired
	private FormDao formDao;

	// Test URL:
	// module/MBI/data/get_design.json?fac_id=83&func_id=904&spd_id=1&func_template_id=&lang_flag=1&admin_user=ADMIN&grp_user_id=
	@RequestMapping(value = "module/MBI/data/get_design.json", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getDesign(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fac_id = request.getParameter("fac_id");
		String func_id = request.getParameter("func_id");
		String spd_id = request.getParameter("spd_id");
		String func_template_id = request.getParameter("func_template_id");
		String lang_flag = request.getParameter("lang_flag");
		String admin_user = request.getParameter("admin_user");
		String grp_user_id = request.getParameter("grp_user_id");

		Map<String, Object> design = new HashMap<String, Object>();

		design.put("fsprelNt", formDao.fsprelNtDao(fac_id, func_id, spd_id));
		design.put("ftrfldNt", formDao.ftrfldNtDao(fac_id, func_id, func_template_id));
		design.put("fxtrelNt", formDao.fxtrelNtDao(fac_id, func_id));
		design.put("grpcolNt", formDao.grpcolNtDao(fac_id, func_id, lang_flag));
		design.put("grpmapNt", formDao.grpmapNtDao(fac_id, func_id));
		design.put("assdefGenNt", formDao.assdefGenNtDao(fac_id, func_id));
		design.put("chtinfNt", formDao.chtinfNtDao(fac_id, func_id));
		design.put("consqlGenNt", formDao.consqlGenNtDao(fac_id, func_id));
		design.put("fscrelNt", formDao.fscrelNtDao(fac_id, func_id, spd_id));
		design.put("mapconGenNt", formDao.mapconGenNtDao(fac_id, func_id, lang_flag));
		design.put("mapdefS2Nt", formDao.mapdefS2NtDao(fac_id, func_id, admin_user));
		design.put("tabvldNt", formDao.tabvldNtDao(fac_id, func_id, spd_id));
		design.put("usrcolNt", formDao.usrcolNtDao(fac_id, func_id, grp_user_id, lang_flag));
		design.put("usrmapNt", formDao.usrmapNtDao(fac_id, func_id, grp_user_id));

		return design;
	}

	@RequestMapping(value = "module/MBI/data/consql_gen_nt.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> consqlGenNt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fac_id = request.getParameter("fac_id");
		String func_id = request.getParameter("func_id");

		return formDao.consqlGenNtDao(fac_id, func_id);
	}

	@RequestMapping(value = "module/MBI/data/fscrel_nt.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> fscrelNt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fac_id = request.getParameter("fac_id");
		String func_id = request.getParameter("func_id");
		String spd_id = request.getParameter("spd_id");

		return formDao.fscrelNtDao(fac_id, func_id, spd_id);
	}

	@RequestMapping(value = "module/MBI/data/mapcon_gen_nt.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> mapconGenNt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fac_id = request.getParameter("fac_id");
		String func_id = request.getParameter("func_id");
		String lang_flag = request.getParameter("lang_flag");

		return formDao.mapconGenNtDao(fac_id, func_id, lang_flag);
	}

	@RequestMapping(value = "module/MBI/data/mapdef_s2_nt.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> mapdefS2Nt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fac_id = request.getParameter("fac_id");
		String func_id = request.getParameter("func_id");
		String admin_user = request.getParameter("admin_user");

		return formDao.mapdefS2NtDao(fac_id, func_id, admin_user);
	}

	@RequestMapping(value = "module/MBI/data/tabvld_nt.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> tabvldNt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fac_id = request.getParameter("fac_id");
		String func_id = request.getParameter("func_id");
		String spd_id = request.getParameter("spd_id");

		return formDao.tabvldNtDao(fac_id, func_id, spd_id);
	}

	@RequestMapping(value = "module/MBI/data/usrcol_nt.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> usrcolNt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fac_id = request.getParameter("fac_id");
		String func_id = request.getParameter("func_id");
		String grp_usr_id = request.getParameter("grp_usr_id");
		String lang_flag = request.getParameter("lang_flag");

		return formDao.usrcolNtDao(fac_id, func_id, grp_usr_id, lang_flag);
	}

	@RequestMapping(value = "module/MBI/data/usrmap_nt.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> usrmapNt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fac_id = request.getParameter("fac_id");
		String func_id = request.getParameter("func_id");
		String grp_usr_id = request.getParameter("grp_usr_id");

		return formDao.usrmapNtDao(fac_id, func_id, grp_usr_id);
	}

	// Test URL: module/MBI/data/fsprel_nt.json?fac_id=83&func_id=904&spd_id=1
	@RequestMapping(value = "module/MBI/data/fsprel_nt.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> fsprelNt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fac_id = request.getParameter("fac_id");
		String func_id = request.getParameter("func_id");
		String spd_id = request.getParameter("spd_id");

		return formDao.fsprelNtDao(fac_id, func_id, spd_id);
	}

	// Test URL:
	// module/MBI/data/ftrfld_nt.json?fac_id=83&func_id=904&func_template_id=1
	@RequestMapping(value = "module/MBI/data/ftrfld_nt.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> ftrfldNt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fac_id = request.getParameter("fac_id");
		String func_id = request.getParameter("func_id");
		String func_template_id = request.getParameter("func_template_id");

		return formDao.ftrfldNtDao(fac_id, func_id, func_template_id);
	}

	// Test URL: module/MBI/data/fxtrel_nt.json?fac_id=83&func_id=904
	@RequestMapping(value = "module/MBI/data/fxtrel_nt.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> fxtrelNt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fac_id = request.getParameter("fac_id");
		String func_id = request.getParameter("func_id");

		return formDao.fxtrelNtDao(fac_id, func_id);
	}

	// Test URL:
	// module/MBI/data/grpcol_nt.json?fac_id=83&func_id=904&lang_falg=1
	@RequestMapping(value = "module/MBI/data/grpcol_nt.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> grpcolNt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fac_id = request.getParameter("fac_id");
		String func_id = request.getParameter("func_id");
		String lang_falg = request.getParameter("lang_falg");

		return formDao.grpcolNtDao(fac_id, func_id, lang_falg);
	}

	// Test URL: module/MBI/data/grpmap_nt.json?fac_id=2&func_id=84
	@RequestMapping(value = "module/MBI/data/grpmap_nt.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> grpmapNt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fac_id = request.getParameter("fac_id");
		String func_id = request.getParameter("func_id");

		return formDao.grpmapNtDao(fac_id, func_id);
	}

	@RequestMapping(value = "module/MBI/data/assdef_gen_nt.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> assdefGenNt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fac_id = request.getParameter("fac_id");
		String func_id = request.getParameter("func_id");

		return formDao.assdefGenNtDao(fac_id, func_id);
	}

	@RequestMapping(value = "module/MBI/data/chtinf_nt.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> chtinfNt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fac_id = request.getParameter("fac_id");
		String func_id = request.getParameter("func_id");

		return formDao.chtinfNtDao(fac_id, func_id);
	}

	@RequestMapping(value = "module/MBI/data/secfundef_nt.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> secfundefNt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fac_id = request.getParameter("fac_id");
		String func_group = request.getParameter("func_group");
		String func_code = request.getParameter("func_code");
		String func_type = request.getParameter("func_type");

		return formDao.secfundefNtDao(fac_id, func_group, func_code, func_type);
	}

}
