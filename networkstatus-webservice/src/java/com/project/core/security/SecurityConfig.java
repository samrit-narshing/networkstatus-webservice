package com.project.core.security;

import com.project.core.util.CryptUtil;
import com.project.core.util.SHA1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Samrit
 */
@Configuration
@EnableWebSecurity
@EnableAsync
@EnableScheduling
public class SecurityConfig
        extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthFailure authFailure;

    @Autowired
    private AuthSuccess authSuccess;

    @Autowired
    private EntryPointUnauthorizedHandler unauthorizedHandler;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    public void configAuthBuilder(AuthenticationManagerBuilder builder) throws Exception {
//        builder.inMemoryAuthentication().withUser("username").password("password").roles("USER");
        builder.userDetailsService(userDetailService)
                .passwordEncoder(new PasswordEncoder() {

                    public String encode(CharSequence rawPassword) {
                        return null; // TODO implement
                    }

                    public boolean matches(CharSequence rawPassword, String encodedPassword) {
                        try {
                            //sha password encryption
                            CryptUtil cryptUtil = new CryptUtil();
                            String encodedRawPassword = (cryptUtil.asHex(SHA1.SHA1(rawPassword.toString())));
                            return encodedRawPassword.equals(encodedPassword); // TODO implement
                        } catch (Exception e) {
                            return false;
                        }
                    }
                });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
//                .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
//        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//We don't need sessions to be created.
                .and()
                .formLogin()
                .successHandler(authSuccess)
                .failureHandler(authFailure)
                .and()
                .authorizeRequests()
                .antMatchers("/core/license/", "/core/license/*").permitAll()
                .antMatchers("/rest/device/test/**").permitAll()
                .antMatchers("/rest/device/**").permitAll()
                .antMatchers("/rest/device/users/find/authcheck/encrypted").permitAll()
                .antMatchers("/rest/device/users/checkServerConnection").permitAll()
                .antMatchers("/rest/device/initialize/data/reset").permitAll()
                .antMatchers("/rest/device/info/control_panel/unsecure/**").permitAll()
                .antMatchers("/rest/device/parent/unsecure/**").permitAll()
                //                .antMatchers("/rest/device/users/find/auth/username/*").permitAll()
                //                .antMatchers("/rest/device/users/find/auth/username/*/password/*").permitAll()
                .antMatchers("/rest/web/**").permitAll()
                .antMatchers("/rest/web/users/find/authcheck/encrypted").permitAll()
                .antMatchers("/rest/web/users/checkServerConnection").permitAll()
                .antMatchers("/rest/web/initialize/data/reset").permitAll()
                .antMatchers("/rest/web/info/control_panel/unsecure/**").permitAll()
                .antMatchers("/rest/web/parent/unsecure/**").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/rest/web/news/**").permitAll()
                .antMatchers("/firebase").permitAll()
                .antMatchers("/**")
                .authenticated();

        
//        http
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
        //extra
//        http
//                .authorizeRequests()
//                .antMatchers("/resources/**").permitAll()
//                .antMatchers("/rest/device/**").permitAll()
//                .antMatchers("/logout").permitAll()
//                .antMatchers("/**")
//                .authenticated()
//                .and()
//                .formLogin().loginPage("/login").failureUrl("/login?error")
//                .usernameParameter("username").passwordParameter("password")
//                .permitAll()
//                .and()
//                .logout().logoutSuccessUrl("/login?logout")
//                .permitAll()
//                .and()
//                //                .exceptionHandling().accessDeniedPage("/403")
//                //                .and()
//                .csrf().disable();
        //end of etra
    }

// Backup    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http
////                .csrf().disable()
////                //                .exceptionHandling()
////                //                    .authenticationEntryPoint(unauthorizedHandler)
////                //                    .and()
////                .formLogin()
////                //                    .successHandler(authSuccess)
////                //                    .failureHandler(authFailure)
////                .and()
////                .authorizeRequests()
////                
////                .antMatchers("/rest/test/**").permitAll()
////                .antMatchers("/**")
////                .authenticated();
//
////        http
////                .authorizeRequests()
////                .anyRequest().authenticated()
////                .and()
////                .formLogin()
////                .loginPage("/login")
////                .permitAll()
////                .and()
////                .logout()
////                .permitAll();
//        //extra
//        http
//                .authorizeRequests()
//                .antMatchers("/resources/**").permitAll()
//                .antMatchers("/rest/device/**").permitAll()
//                .antMatchers("/logout").permitAll()
//                .antMatchers("/**")
//                .authenticated()
//                .and()
//                .formLogin().loginPage("/login").failureUrl("/login?error")
//                .usernameParameter("username").passwordParameter("password")
//                .permitAll()
//                .and()
//                .logout().logoutSuccessUrl("/login?logout")
//                .permitAll()
//                .and()
//                //                .exceptionHandling().accessDeniedPage("/403")
//                //                .and()
//                .csrf().disable();
//        //end of etra
//    }
    @Order(1)
    @Configuration
    static class resoucesAccessConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .headers().disable()
                    .antMatcher("/resources/**")
                    .authorizeRequests().anyRequest().permitAll();
        }
    }
    
    
    
    
//     @Override
//    protected void configure(HttpSecurity http) throws Exception {
//  
//      http.csrf().disable()
//        .authorizeRequests()
//        .antMatchers("/user/**").hasRole("ADMIN")
//        .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
//        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.
//    }
//     
//    @Bean
//    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
//        return new CustomBasicAuthenticationEntryPoint();
//    }
}
