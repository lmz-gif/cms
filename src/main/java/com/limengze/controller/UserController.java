package com.limengze.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.limengze.commons.UserConst;
import com.limengze.dao.CategoryMapper;
import com.limengze.entity.Article;
import com.limengze.entity.ArticleType;
import com.limengze.entity.Category;
import com.limengze.entity.ImageBean;
import com.limengze.entity.User;
import com.limengze.service.ArticleService;
import com.limengze.service.CategoryService;
import com.limengze.service.UserService;

/**
 * @author lmz
 * @Date 2019年10月16日
 * 
 */

@RequestMapping("user")
@Controller
public class UserController {
	
	@Autowired
	RedisTemplate<String, Category> redis;
	
	@Autowired
	UserService userService;

	@Autowired
	ArticleService as;
	
	@Autowired
	CategoryService categoryService;
	
	
	// 跳转到注册页面
	@GetMapping("register")
	public String register() {
		return "user/register";
	}
	
	//进入登录页面
	@GetMapping("login")
	public String toLogin() {
		return "user/login";
	}
	
	//用户注册唯一校验
	@RequestMapping("checkExist")
	@ResponseBody
	public boolean checkExist(String username) { 
		boolean res = userService.checkExist(username);
		return !res;
	}
	
	/**
	 * 注册及验证
	 * @param user
	 * @param errorResult
	 * @param model
	 * @return
	 */
	@PostMapping("register")
	public String register(@Validated User user, BindingResult errorResult, Model model) {
		
		if (errorResult.hasErrors()) {  // validate验证
			return "user/register";
		}
		
		int res = userService.register(user);     // 获取注册结果
		if (res > 0) {
			return "redirect:login";
		} else {
			model.addAttribute("msg", "注册失败,请稍后再试");
			return "user/register";
		}
	}
	
	/**
	 * 登录
	 * @param user
	 * @param request
	 * @param errorRes
	 * @return
	 */
	@PostMapping("login")
	public String login(@Validated User user, HttpServletRequest request, BindingResult errorRes) {
		
		if (errorRes.hasErrors()) {
			return "login";
		}
		
		User login = userService.login(user);         // 调用登录判断
		if (login == null) {
			request.setAttribute("error", "用户名或密码错误!");
			return "user/login";
		} else {
			if (login.getLocked() == 0) {             // 判断用户是否被冻结
				request.getSession().setAttribute(UserConst.SESSION_USER_KEY, login);
				// 判断
				if (login.getRole() == UserConst.USER_ROLE_GENERAL) {
					return "redirect:home";           // 普通用户重定向到主页
				} else if (login.getRole() == UserConst.USER_ROLE_ADMIN) {
					return "redirect:../admin/index"; // 管理员重定向到管理页
				} else {
					return "user/login";              // 其他情况
				}
			} else {
				request.setAttribute("error", "该用户已被冻结!");
				return "user/login";
			}
		}
		
	}
	
	
	/**
	 * 登出
	 * @param session
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute(UserConst.SESSION_USER_KEY);
		return "user/login";
	}
	
	
	/**
	 * 跳转到个人中心(普通用户)
	 * @return
	 */
	@RequestMapping("home")
	public String toHome() {
		return "my/home";
	}
	
	
	//用户文章列表
	@RequestMapping("myArticles")
	public String myArticles(HttpServletRequest request, 
			@RequestParam(defaultValue="1")Integer pageNum ,@RequestParam(defaultValue="")String titles,@RequestParam(defaultValue="0")Integer categoryId) {
		
		User loginUser = (User) request.getSession().getAttribute(UserConst.SESSION_USER_KEY);  // 获取登录用户的信息
		PageInfo<Article> articles = userService.myArticles(pageNum, loginUser.getId(),titles, categoryId);
		ListOperations<String, Category> opsForList = redis.opsForList();
		List<Category> categories=null;
		if (redis.hasKey("category")) {
			categories = opsForList.range("category", 0, -1);
		}else {
			categories = categoryService.getList();
			opsForList.leftPushAll("category", categories);
		}
		request.setAttribute("articles", articles);
		request.setAttribute("categories", categories);
		
		return "/my/list";
	}
	
	
	//进入发布页面
	@RequestMapping("toPublish")
	public String toPublish() {
		return "article/publish";
	}
	
	//进入发布图片页面
	@RequestMapping("toPublistImgs")
	public String toPublistImgs() {
		return "article/publishimg";
	}
	
	
	/**
	 * 文章添加TEXT
	 * @param request
	 * @param article
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("publish")
	@ResponseBody
	public boolean publish(HttpSession session, Article article, MultipartFile file) throws IllegalStateException, IOException {
		
		article.setArticleType(ArticleType.TEXT);          // 类型为HTML
		prossesFile(file, article);     // 处理文件上传
		
		User loginUser = (User) session.getAttribute(UserConst.SESSION_USER_KEY);   // 获取登录的用户信息
		article.setUserId(loginUser.getId());       // 将登录用户信息存入传递类
		
		int res = userService.publish(article);
		
		return res > 0;
	}
	
	/**
	 * 	文章添加IMG
	 * @param request
	 * @param article
	 * @param file
	 * @param imgs
	 * @param imgsdesc
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("publishimg")
	@ResponseBody
	public boolean publishimg(HttpServletRequest request, Article article, 
			@RequestParam("file") MultipartFile file,   // 标题图片
			@RequestParam("imgs") MultipartFile[] imgs, // 文章中图片
			@RequestParam("imgsdesc") String[] imgsdesc // 文章中图片的描述
				) throws IllegalStateException, IOException {
		
		article.setArticleType(ArticleType.IMAGE);      // 文章类型为Image
		
		prossesFile(file,article);                      // 将标题图片保存
		List<ImageBean> imgBeans =  new ArrayList<ImageBean>();      // 创建一个空的图片信息集合
		
		for (int i = 0; i < imgs.length; i++) {
			String url = prossesFile(imgs[i]);
			if(!"".equals(url)) {
				ImageBean imageBean = new ImageBean(url, imgsdesc[i]);
				imgBeans.add(imageBean);
			}
		}
		
		Gson gson = new Gson();
		String json = gson.toJson(imgBeans);// 文章的内容
		article.setContent(json);//
		
		User loginUser = (User) request.getSession().getAttribute(UserConst.SESSION_USER_KEY);   // 获取登录的用户信息
		article.setUserId(loginUser.getId());       // 将登录用户信息存入传递类
		
		int res = userService.publish(article);              // 进行发布
		
		return res > 0;
	}
	
	
	/**
	 * 	用户删除文章(逻辑删除)
	 * @param id         文章ID
	 * @return
	 */
	@RequestMapping("delArticle")
	@ResponseBody
	public boolean delArticle(Integer id) {
		int res = userService.delArticle(id);
		return res > 0;
	}
	
	/**
	 * 	用户批量删除文章(逻辑删除)
	 * @param id         文章ID
	 * @return
	 */
	@RequestMapping("delMore")
	@ResponseBody
	public boolean delMore(String ids) {
		ids = ids.substring(1);
		int res = userService.delMore(ids);
		return res > 0;
	}
	
	
	/**
	 * 	用户修改文章提交
	 * @param session
	 * @param article        文章
	 * @param file           上传的文件
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@PostMapping("update")
	@ResponseBody
	public boolean update(HttpSession session, Article article, MultipartFile file) throws IllegalStateException, IOException {
		
		prossesFile(file, article);      // 处理上传文件
		
		User loginUser = (User) session.getAttribute(UserConst.SESSION_USER_KEY);   // 获取登录的用户信息
		article.setUserId(loginUser.getId());       // 将登录用户信息存入传递类
		
		int res = userService.updateArt(article);
		
		return res > 0;
	}
	
	
	/**
	 * 	图片文件处理
	 * @param file
	 * @param article
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	//添加到文章对象
	private void prossesFile(MultipartFile file, Article article) throws IllegalStateException, IOException {
		
		String filename = file.getOriginalFilename();                  // 获取文件名称
		if ("".equals(filename) || file.isEmpty()) {                   // 对获取的文件名进行非空判断
			article.setPicture("");
			return;
		}
		String suffix = filename.substring(filename.lastIndexOf(".")); // 获取文件后缀名称
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String today = sdf.format(new Date());
		
		// 生成路径
		String path = "D:/img/" + today;
		
		File menu = new File(path);
		if (!menu.exists()) {        // 如果文件不存在则创建文件
			menu.mkdir();
		}
		
		String newName = UUID.randomUUID().toString() + suffix;     // 生成新文件名称
		String finalPath = path +"/"+ newName;                   		 // 生成最终文件路径
		String dbPath ="/img/"+ today+"/" + newName;                      // 生成数据库文件路径
		
		File saveFile = new File(finalPath);                        // 最终
		file.transferTo(saveFile);                                  // 保存文件
		article.setPicture(dbPath);                                 // 数据库保存文件路径
	}
	
	//返回文件路径
	private String prossesFile(MultipartFile file) throws IllegalStateException, IOException {
		
		String filename = file.getOriginalFilename();                  // 获取文件名称
		if ("".equals(filename) || file.isEmpty()) {                   // 对获取的文件名进行非空判断
			return "";
		}
		String suffix = filename.substring(filename.lastIndexOf(".")); // 获取文件后缀名称
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String today = sdf.format(new Date());
		
		// 生成路径
		String path = "D:/img/" + today;
		
		File menu = new File(path);
		if (!menu.exists()) {        // 如果文件不存在则创建文件
			menu.mkdir();
		}
		
		String newName = UUID.randomUUID().toString() + suffix;     // 生成新文件名称
		String finalPath = path + "/" + newName;                    // 生成最终文件路径
		String dbPath = "/img/"+today + "/" + newName;                      // 生成数据库文件路径
		
		File saveFile = new File(finalPath);                        // 最终
		file.transferTo(saveFile);                                  // 保存文件
		return dbPath;                                 // 返回数据库保存文件路径
	}
	
}

