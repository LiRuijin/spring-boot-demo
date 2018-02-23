/**
 * @创建人：李瑞金
 * @创建日期：2018年2月23日下午10:10:29
 */
package com.demo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.listener.DemoListener;

@WebServlet(name = "IndexServlet",urlPatterns = "/hello")
public class DemoServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(DemoListener.class);
	
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("hello word");
        resp.getWriter().flush();
        resp.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
