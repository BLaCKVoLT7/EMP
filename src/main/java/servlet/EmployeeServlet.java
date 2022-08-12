package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Employee;
import serviceAndUtil.EmployeeDao;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/")
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private EmployeeDao UserDao ;
 

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
	
		UserDao = new EmployeeDao() ;
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}




	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

	
			switch (action) {
			
			case "/new":
				showNewForm(request, response) ;
				break;
				
			case "/insert":
				try {
				insertUser(request, response);
				}catch( SQLException e ) {
					e.printStackTrace();
				}
				break;
				
			case "/delete":
				try {
				deleteUser(request, response);
				}catch( SQLException e ) {
					e.printStackTrace();
				}
				break;
				
				
			case "/edit":
				try {
				showEditForm(request, response);
				}catch( SQLException e ) {
					e.printStackTrace();
				}
				break;
				
			case "/update":
				try {
				updateUser(request, response);
				}catch( SQLException e ) {
					e.printStackTrace();
				}
				break;
				
			default:
				try {
				listUser(request, response);
				}catch( SQLException e ) {
					e.printStackTrace();
				}
				break;
			}
			
		
	}
	
	// showNewForm()
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
		dispatcher.forward(request, response);
		
		}
	
	// Insert user
	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		
		String name = request.getParameter("name") ;
		String email = request.getParameter("email") ;
		String country = request.getParameter("country") ;
		
		Employee newUser = new Employee(name, email, country);
		
		UserDao.insertUser(newUser);
		response.sendRedirect("list") ; 
	}
	
	// Delete User
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id")) ;
		
		try {
			
		UserDao.deleteUser(id);
		
		} catch( Exception e ) {
			
			e.printStackTrace() ;
			
		} 
		
		response.sendRedirect("list");

	}
	
	// Edit
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		Employee existingUser = UserDao.selectUser(id);
		
		try {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
	
		request.setAttribute("user", existingUser);
		
		dispatcher.forward(request, response);

		}catch( Exception e ) {
			
			e.printStackTrace();
			
		}
		
	}
	
	
	// Update User
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");

		Employee book = new Employee(id, name, email, country);
		UserDao.updateUser(book);
		
		response.sendRedirect("list");
	}
	
	// Default Method
	private void listUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		try {
		List<Employee> listUser = UserDao.selectAllUsers();
		
		request.setAttribute("listUser", listUser);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("employee-list.jsp");
		
		dispatcher.forward(request, response);
	
		} catch( Exception e ) {
			
			e.printStackTrace() ;
			
		}
		
	}
		
	

}	