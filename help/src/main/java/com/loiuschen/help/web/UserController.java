package com.loiuschen.help.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loiuschen.help.domain.User;
import com.loiuschen.help.domain.UserReg;
import com.loiuschen.help.service.MailService;
import com.loiuschen.help.service.UserService;

@Controller
public class UserController {
	@Autowired
	private  UserService userService;
	
	@RequestMapping(value = "/getCodeAPI", method = RequestMethod.GET)
	void SaveUserRegRequest(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String retMsg = "";
		try {
			request.setCharacterEncoding("UTF-8");
			String name = request.getParameter("name");
            String cellphone = request.getParameter("cellphone");
            String email = request.getParameter("email");
            String id = request.getParameter("id");

			retMsg = userService.SaveUserRegQuestInfo(name, cellphone, email, id);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(retMsg);
	}
	
	@RequestMapping(value = "/loginAPI", method = RequestMethod.GET)
	void GetUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String jsonUserInfo = "";
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			jsonUserInfo = userService.GetUserInfo(username, password);
			
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		}
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(jsonUserInfo);
	}
	
	@RequestMapping(value = "/getRegInfoAPI", method = RequestMethod.GET)
	void GetRegInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String jsonUserInfo = userService.GetRegInfoList();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(jsonUserInfo);
	}
	
	
	@RequestMapping(value = "/sendRegEmailAPI", method = RequestMethod.GET)
	void SendRegEmail(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String jsonUserInfo = "";
		response.setCharacterEncoding("UTF-8");
		String idFind = request.getParameter("id");
		UserReg userReginfo = userService.GetRegInfo(idFind);
		if(null != userReginfo)
		{
			try {
				
				String id = userReginfo.getId();
				String name = userReginfo.getName();
				String cellphone = userReginfo.getCellphone();
				String email = userReginfo.getEmail();
				String securityCode = userReginfo.getSecurityCode();
				String strUrl = String.format("http://39.105.27.80/help/reg?name=%s&cellphone=%s&email=%s&id=%s&securityCode=%s", 
						name, cellphone, email, id, securityCode);
				String linkAddress = "<a href='" + strUrl + "'>点击激活注册 </a>";
				MailService.sendMail(email, "注册激活邮件", linkAddress);
				
				userService.UpdateRegInfo(id);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("retCode", 500);
				map.put("msg", "已给用户发送注册激活邮件！");
				JsonConfig sc = new JsonConfig();
				jsonUserInfo = JSONObject.fromObject(map, sc).toString();
				
			} catch (Exception e) {
				e.printStackTrace();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("retCode", 501);
				map.put("msg", "已给用户发送注册激活邮件失败！");
				JsonConfig sc = new JsonConfig();
				jsonUserInfo = JSONObject.fromObject(map, sc).toString();
			}
		}
		else
		{
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(jsonUserInfo);
	}
	
	@RequestMapping(value = "/deleteRegAPI", method = RequestMethod.GET)
	void DeleteRegInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		String idDelte = request.getParameter("id");
		userService.deleteReg(idDelte);
	}
	
	@RequestMapping(value = "/requestRegAPI", method = RequestMethod.GET)
	void RequestReg(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String retMsg = "";
		try {
			request.setCharacterEncoding("UTF-8");
			String name = request.getParameter("name");
            String cellphone = request.getParameter("cellphone");
            String email = request.getParameter("email");
            String id = request.getParameter("id");
            String securityCode = request.getParameter("securityCode");
            String password = request.getParameter("password");
            
            UserReg userRegRequestInfo = userService.GetRegInfo(id);
            String nameTmp = userRegRequestInfo.getName();
            String cellphoneTmp = userRegRequestInfo.getCellphone();
            String emailTmp = userRegRequestInfo.getEmail();
            String idTmp = userRegRequestInfo.getId();
            String securityCodeTmp = userRegRequestInfo.getSecurityCode();
            if(nameTmp.equals(name) && cellphoneTmp.equals(cellphone) && emailTmp.equals(email) 
            		&& idTmp.equals(id) && securityCodeTmp.equals(securityCode))
            {
            	userService.SaveUserInfo(name, cellphone, email,
                        id, securityCode, password);
            	Map<String, Object> map = new HashMap<String, Object>();
				map.put("retCode", 500);
				map.put("msg", "注册成功请登录！");
				JsonConfig sc = new JsonConfig();
				retMsg = JSONObject.fromObject(map, sc).toString();
            }
            else
            {
            	Map<String, Object> map = new HashMap<String, Object>();
				map.put("retCode", 501);
				map.put("msg", "注册失败请重新提交注册申请！");
				JsonConfig sc = new JsonConfig();
				retMsg = JSONObject.fromObject(map, sc).toString();
            }
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("retCode", 501);
			map.put("msg", "注册失败请重新提交注册申请！");
			JsonConfig sc = new JsonConfig();
			retMsg = JSONObject.fromObject(map, sc).toString();
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(retMsg);
	}
	
	@RequestMapping(value = "/getUserInfoAPI", method = RequestMethod.GET)
	void GetUserlistInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String retMsg = "";
		try {
			retMsg = userService.GetUsrSumInfo();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("retCode", 501);
			map.put("userInfo", null);
			JsonConfig sc = new JsonConfig();
			retMsg = JSONObject.fromObject(map, sc).toString();
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(retMsg);
	}
	
	@RequestMapping(value = "/removeUserAPI", method = RequestMethod.GET)
	void removeUser(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		String idDelte = request.getParameter("id");
		userService.deleteUsr(idDelte);
		userService.DeleteUserSumJizhang(idDelte);
		userService.deleteReg(idDelte);
		
		String retMsg = "";
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(retMsg);
	}
	
	@RequestMapping(value = "/getTixianInfoAPI", method = RequestMethod.GET)
	void getNotExcutetixianinfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String jsonUserInfo = userService.GetbnotExcutedTixianInfoList();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(jsonUserInfo);
	}
	
	@RequestMapping(value = "/excuteTixianInfoAPI", method = RequestMethod.GET)
	void excuteTixianInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		String tixianid = request.getParameter("tixianid");
		String id = request.getParameter("id");
		String tixianriqi = request.getParameter("tixianriqi");
		String tixiane = request.getParameter("tixiane");
		String msg = userService.excuteTixianInfo(tixianid, id, tixianriqi, tixiane);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(msg);
	}
	
	@RequestMapping(value = "/gettixianmessageAPI", method = RequestMethod.GET)
	void gettixianmessage(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("id");
		String msg = userService.GetTixianMessageList(userId);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(msg);
	}
	
	
	@RequestMapping(value = "/userAlreadReadmessageAPI", method = RequestMethod.GET)
	void userAlreadReadmessage(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		String tixianid = request.getParameter("tixianid");
		String msg = userService.updateMessageState(tixianid);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(msg);
	}
	
	@RequestMapping(value = "/modifyuserPWAPI", method = RequestMethod.GET)
	void modifyuserPWAPI(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("id");
		String password = request.getParameter("password");
		String msg = userService.modifyPW(userId, password);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(msg);
	}
	
	@RequestMapping(value = "/getUserJizhangInfoAPI", method = RequestMethod.GET)
	void getUserJizhangInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("id");
		String msg = userService.GetUserJizhangInfo(userId);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(msg);
	}
	
	@RequestMapping(value = "/ModifyUserJizhangInfoAPI", method = RequestMethod.GET)
	void ModifyUserJizhangInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("id");
		String jizhangriqi = request.getParameter("jizhangriqi");
		String xinzengtouzie = request.getParameter("xinzengtouzie");
		String tixiane = request.getParameter("tixiane");
		String shouyie = request.getParameter("shouyie");
		String touzitianshu = request.getParameter("touzitianshu");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(true == userService.bCheckUserExist(userId)){
			
			userService.AddUserJizhangDayInfo(userId, jizhangriqi, 
					xinzengtouzie, shouyie, tixiane, touzitianshu);
			
			Double touzieIn = Double.parseDouble(xinzengtouzie);
			if(touzieIn != null && touzieIn > 0 )
			{
				userService.AddTouziRec(userId, jizhangriqi, xinzengtouzie, touzitianshu);
			}
			else{
				userService.DeleteTouziRec(userId, jizhangriqi);
			}
			
			Double shoyieIn = Double.parseDouble(shouyie);
			if(shoyieIn != null && shoyieIn > 0 )
			{
				userService.AddShouyiRec(userId, jizhangriqi, shouyie);
			}
			else
			{
				userService.DeleteShouyiRec(userId, jizhangriqi);
			}
			
			Double tixianeIn = Double.parseDouble(tixiane);
			if(tixianeIn != null && tixianeIn > 0 )
			{
				userService.AddTixianRec(userId, jizhangriqi, tixiane);
			}
			else
			{
				userService.DeleteTixianRec(userId, jizhangriqi);
			}
			
			userService.ModifyUserJizhangSumInfo(userId, shouyie, jizhangriqi);
			
			userService.ModifyUserNianhualv(userId);
			
			map.put("retCode", 500);
			map.put("msg", "投资收益记账成功！");
			
		}else{
			map.put("retCode", 501);
			map.put("msg", "投资收益人填写错误！");
		}
		
		JsonConfig sc = new JsonConfig();
		String msg = JSONObject.fromObject(map, sc).toString();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(msg);
	}
	
	@RequestMapping(value = "/DeleteUserJizhangInfoAPI", method = RequestMethod.GET)
	void DeleteUserJizhangInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		
		String jizhangid = request.getParameter("jizhangid");
		String userId = request.getParameter("id");
		String jizhangriqi = request.getParameter("jizhangriqi");
		
		userService.DeleteUserJizhangInfo(jizhangid, userId, jizhangriqi);
		
		boolean bExistuserJizhang = userService.bUserJizhangExist(userId);
		if(bExistuserJizhang != true)
		{
			userService.DeleteUserSumJizhang(userId);
		}
		else{
			userService.DeleteTouziRec(userId, jizhangriqi);
			
			userService.DeleteShouyiRec(userId, jizhangriqi);
			
			userService.DeleteTixianRec(jizhangriqi, jizhangriqi);
			
			//重新统计记账汇总
			userService.ModifyUserJizhangSumInfo(userId);
			
			userService.ModifyUserNianhualv(userId);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", 500);
		map.put("msg", "记账删除成功！");
		JsonConfig sc = new JsonConfig();
		String msg = JSONObject.fromObject(map, sc).toString();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(msg);
	}
	
	@RequestMapping(value = "/GetSumbangdanInfoAPI", method = RequestMethod.GET)
	void GetSumbangdanInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("id");
		String msg = userService.GetSumbangdanInfo(userId);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(msg);
	}
	
	@RequestMapping(value = "/GetJizhangSumInfoAPI", method = RequestMethod.GET)
	void GetJizhangSumInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("id");
		String msg = userService.GetJizhangSumInfo(userId);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(msg);
	}
	
	@RequestMapping(value = "/AddUserJizhangDayInfoAPI", method = RequestMethod.GET)
	void AddUserJizhangDayInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("id");
		String jizhangriqi = request.getParameter("jizhangriqi");
		String xinzengtouzie = request.getParameter("xinzengtouzie");
		String tixiane = request.getParameter("tixiane");
		String shouyie = request.getParameter("shouyie");
		String touzitianshu = request.getParameter("touzitianshu");
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(true == userService.bCheckUserExist(userId)){//用户是否存在
			userService.AddUserDayJizhangInfo(userId, jizhangriqi, xinzengtouzie, tixiane,
					shouyie, touzitianshu, map);
			
		}else{
			map.put("retCode", 501);
			map.put("msg", "投资收益人填写错误！");
		}
		
		JsonConfig sc = new JsonConfig();
		String msg = JSONObject.fromObject(map, sc).toString();		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(msg);
	}
	
	
	@RequestMapping(value = "/DeleteUserSumJizhangAPI", method = RequestMethod.GET)
	void DeleteUserSumJizhang(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		String idDelte = request.getParameter("id");
		userService.DeleteUserSumJizhang(idDelte);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", 500);
		JsonConfig sc = new JsonConfig();
		String msg = JSONObject.fromObject(map, sc).toString();		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(msg);
	}
	
	@RequestMapping(value = "/SaveUserInfoAPI", method = RequestMethod.GET)
	void SaveUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("UTF-8");
        String cellphone = request.getParameter("cellphone");
        String email = request.getParameter("email");
        String id = request.getParameter("id");

        String retMsg = userService.ModifyuserInfo(id, cellphone, email);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(retMsg);
	}
	
	@RequestMapping(value = "/AddPLShouyiInfoAPI", method = RequestMethod.GET)
	void AddPLShouyiInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		String jizhangriqi = request.getParameter("jizhangriqi");
		String shouyie = request.getParameter("shouyie");
		Double dShouyie = Double.valueOf(shouyie);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(dShouyie == null || dShouyie <= 0)
		{
			map.put("retCode", 501);
			map.put("msg", "批量添加用户收益失败！");
		}
		else{
			List<User> allUser = userService.GetAllUser();
			//计算所有用户的投资比例
			Map<String, Double> rateMap = userService.GetUserTouziRate();
			
			for(User user: allUser)
			{
				String userId = user.getId();
				
				Double rate = rateMap.get(userId);
				
				if(rate == null || rate <= 0)
				{
					break;
				}
				
				Double dShoyiRateE = rate * dShouyie;
				
				userService.AddUserJizhangDayInfo(userId, jizhangriqi, 
						"0", dShoyiRateE.toString(), "0", "0");
				
				userService.AddShouyiRec(userId, jizhangriqi, dShoyiRateE.toString());
				
				userService.AddUserJizhangSumInfo(userId, jizhangriqi, 
						"0", dShoyiRateE.toString(), "0");
			}
			
			map.put("retCode", 500);
			map.put("msg", "投资收益增加成功！");
			
		}
		
		JsonConfig sc = new JsonConfig();
		String msg = JSONObject.fromObject(map, sc).toString();		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(msg);
	}
	
	@RequestMapping(value = "/GetOptRecInfoAPI", method = RequestMethod.GET)
	void GetOptRecInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String jsonUserInfo = userService.GetOptRecInfo();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(jsonUserInfo);
	}
	
	
	@RequestMapping(value = "/ClearOptRecInfoAPI", method = RequestMethod.GET)
	void ClearOptRecInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String jsonUserInfo = userService.ClearOptRecInfo();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(jsonUserInfo);
	}
	
	@RequestMapping(value = "/BackdataInfoAPI", method = RequestMethod.GET)
	void BackdataInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String jsonbackInfo = userService.BackdataInfo();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(jsonbackInfo);
	}
	
	@RequestMapping(value = "/GetbackrecInfoAPI", method = RequestMethod.GET)
	void GetbackrecInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String jsonbackInfo = userService.GetbackrecInfo();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(jsonbackInfo);
	}
	
}
