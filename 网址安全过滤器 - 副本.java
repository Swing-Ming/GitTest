	/**
	*需要在request里面获取uri地址，在将此地址与过滤器
	*配置里面的地址对比，如果以过滤器里面的地址开头，
	*就要对session进行判断
	*/
	
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();

		String[] strs = uris.split(",");
		boolean flag = false;
		for (String str : strs) {
			if (uri.startsWith(str)) {
				flag = true;
				break;
			}
		}

		if (flag) {
			chain.doFilter(request, response);
		} else {

			HttpSession session = req.getSession();
			Object loginname = session.getAttribute("username");
			if (loginname == null) {
				res.sendRedirect("/index");
			} else {
				chain.doFilter(request, response);
			}
		}

	}
	
	//获取过滤器里面配置的地址  uris
		public void init(FilterConfig fConfig) throws ServletException {
		uris = fConfig.getInitParameter("uris");
	}