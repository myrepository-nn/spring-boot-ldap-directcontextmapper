package com.nishant.spring.boot.ldap;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.AbstractContextMapper;

public class PersonContextMapper  extends AbstractContextMapper<Person>  {

	@Override
	protected Person doMapFromContext(DirContextOperations ctx) {
		Person p=new Person();
		p.setFullname(ctx.getStringAttribute("cn"));
		p.setLastname(ctx.getStringAttribute("sn"));
		p.setDescription(ctx.getStringAttribute("description"));
		p.setGivenname(ctx.getStringAttribute("givenname"));
		p.setMail(ctx.getStringAttribute("mail"));
		//p.setManager(ctx.getStringAttribute("manager"));
		p.setUid(ctx.getStringAttribute("uid"));
		//p.setUserpassword();
		return p;
	}
}