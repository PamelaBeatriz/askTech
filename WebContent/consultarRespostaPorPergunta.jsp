<%@ include file="cabecalho.jsp"%>

<%
    
	String idPergunta = request.getParameter("idPergunta");
	String perguntaDescricao = request.getParameter("titulo");
	String perguntaAutor = request.getParameter("autor");
	String perguntaTitulo = request.getParameter("descricao");
	
	if(request.getAttribute("titulo") != null) {
		perguntaDescricao = (String)request.getAttribute("titulo");
		perguntaAutor = (String)request.getAttribute("autor");
		perguntaTitulo = (String)request.getAttribute("descricao");
	}
	
	usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
%>

<form id="formConsultarRespostaPorPergunta"
	action="ServletConsultarRespostaPergunta" method="post">
	
	<div id="site_content">	
			<div style="width: 1000px;" class="content">	
				<h1>
					<output name="titulo"><%=perguntaDescricao%></output>
					<output name="autor" style="font-size: 16px; float: right;">Por: <%=perguntaAutor%></output>
				</h1>
				&nbsp;&nbsp;&nbsp;<output name="descricao" style="margin-left: 5px;"><%=perguntaTitulo%></output>
				<br>
				<br>
				<p>&nbsp;&nbsp;&nbsp;
 		     <% 	if((usuarioLogado != null && usuarioLogado.getNome().equals(perguntaAutor))) { %>
					<input id="submitMenor" value="Alterar" type="submit" name="alterarPergunta"/>	
 					<%} 
					if((usuarioLogado != null && usuarioLogado.isAdmin()) ||  
							(usuarioLogado != null && usuarioLogado.getNome().equals(perguntaAutor))) { %>											    		
					<input id="submitMenor" value="Excluir" type="submit" name="excluirPergunta"/>	    		
 					<%} %> 
						<input type="hidden" value="<%=idPergunta%>" name="idPerguntaSelecionada"> 
				</p>
			</div>
	</div>
	<div id="site_content">		
		<%
			int aux = 0;
			int idResposta;
			int idAutorResposta;
		%>
		<c:forEach items="${resposta}" var="resposta">
			<%
				ArrayList<Resposta> respostas = (ArrayList<Resposta>)request.getAttribute("resposta");
				idResposta = respostas.get(aux).getIdResposta();
				idAutorResposta = respostas.get(aux).getIdUsuario();
				aux++;
			%>
			<div class="content">
				<div class="content_item">
					<div  style="width: 900px;" class="form_settings_cadastro">
						<p>
						    <c:out value="${resposta.descricao}"></c:out>						
							<span style="float: right; font-weight:bold;">
							   	<%
							   		VotoBC votoBC = new VotoBC();
							   						   	    
							   					   	 		if(usuarioLogado != null) {
							   					   				Boolean liked = votoBC.consultarVoto(usuarioLogado.getIdUsuario(), idResposta);
							   					   				
							   				          			if(liked == false) {
							   					          			liked = true;
							   	%>
											<a href="<%=getServletContext().getContextPath()%>/ServletCadastroResposta?idR=${resposta.idResposta}&idUser=<%=usuarioLogado.getIdUsuario()%>&liked=<%=liked%>">
											<img style="width:40px;" src="images/dislike.gif"></a>
								<%
						        		}else if(liked == true) {
							          		liked = false;
								%>	
											<a href="<%=getServletContext().getContextPath()%>/ServletCadastroResposta?idR=${resposta.idResposta}&idUser=<%=usuarioLogado.getIdUsuario()%>&liked=<%=liked%>">
											<img style="width:40px;" src="images/like.gif"></a>
								<%
										}
						          	}								
	 								if(idPergunta != null) {
		 								session.setAttribute("idPergunta", idPergunta);
		 							}
	 								session.setAttribute("isVotar", true);
									session.setAttribute("descricao", perguntaTitulo);
									session.setAttribute("autor", perguntaAutor);
									session.setAttribute("titulo", perguntaDescricao);
								%>
	 							<br>Votos: ${resposta.votos}
							</span>					
						<p>
							<output style="font-size: 11px; position: right;">Por: <c:out value="${resposta.nomeUsuario}"></c:out></output>
							<br>						
							<output style="font-size: 9px; position: right;">Em: <c:out value="${resposta.data}"></c:out> às <c:out value="${resposta.hora}"></c:out></output>
						</p>
					
						<p>
						<% 	if((usuarioLogado != null && usuarioLogado.getIdUsuario() == idAutorResposta)) { %>
								<a id="submitMenor" href="<%=getServletContext().getContextPath()%>/ServletConsultarRespostaPergunta?alterarResposta=Alterar&idRespostaSelecionada=${resposta.idResposta}">Alterar</a>
							<%}
								if((usuarioLogado != null && usuarioLogado.isAdmin()) || 
								(usuarioLogado != null && usuarioLogado.getIdUsuario() == idAutorResposta)) { %>											    		
								<a id="submitMenor" href="<%=getServletContext().getContextPath()%>/ServletConsultarRespostaPergunta?excluirResposta=Excluir&idRespostaSelecionada=${resposta.idResposta}">Excluir</a>    		
					    	<%} %>
						</p>		
						<input type="hidden" name="idRespostaSelecionada" value="${resposta.idResposta}">
					</div>
				</div>
			</div>
		</c:forEach>
		<a href="usuarioAutenticado/responder.jsp">
			<input class="submit" type="button" name="btn_responder" value="Responder" />
		</a>
	</div>
</form>

<script>
	function submeterformConsultarRespostaPorPergunta(id) {
		var formulario;
		formulario = document.getElementById(id);
		alert(id);
		formulario.submit();
	}
</script>

<jsp:include page="rodape.jsp"></jsp:include>