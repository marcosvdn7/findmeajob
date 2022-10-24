package com.entra21.findmeajob.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.entra21.findmeajob.repository.UsuarioRepository;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private LoginSucesso loginSucesso;
	
	@Bean
	public BCryptPasswordEncoder gerarCriptografia() {
		BCryptPasswordEncoder criptografia = new BCryptPasswordEncoder();
		return criptografia;
	}
	
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		DetalheUsuarioServico detalheDoUsuario = new DetalheUsuarioServico(usuarioRepository);
		return detalheDoUsuario;
	}
	
	
	//- Configuração das permissões, aqui seleciona quais são as request com os papeis de usuários
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/usuario/*").hasAnyAuthority("USUARIO","ADMIN")
        .antMatchers("/admin/*").hasAnyAuthority("ADMIN")
        .and()
        .exceptionHandling().accessDeniedPage("/auth/auth-acesso-negado")
        .and()
        .formLogin().successHandler(loginSucesso) //o spring entende que o login foi um sucesso, criando as views e jogando o usuario pra pagina correta
        .loginPage("/login").permitAll()
        .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/usuarios/login").permitAll();
        
        
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// O objeto que vai obter os detalhes do usuário
		UserDetailsService detalheDoUsuario = userDetailsServiceBean();
		// Objeto para criptografia
		BCryptPasswordEncoder criptografia = gerarCriptografia();
		
		auth.userDetailsService(detalheDoUsuario).passwordEncoder(criptografia);
	}


}
