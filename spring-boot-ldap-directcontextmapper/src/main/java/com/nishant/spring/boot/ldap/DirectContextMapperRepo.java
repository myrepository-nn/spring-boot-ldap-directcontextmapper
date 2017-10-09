package com.nishant.spring.boot.ldap;

import java.util.List;

import javax.naming.Name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;

public class DirectContextMapperRepo {

	@Autowired
	private LdapTemplate ldapTemplate;
	@Autowired
	private ContextMapper<Person> contextMapper;

	protected Name buildDn(Person person) {
		return buildDn(person.getFullname());
	}

	protected Name buildDn(String fullname) {
		return LdapNameBuilder.newInstance()
				.add("ou", "people")
				.add("cn", fullname)
				.build();
	}

	public void create(Person person) {
		DirContextAdapter context = new DirContextAdapter(buildDn(person));
		mapToContext(person, context);
		ldapTemplate.bind(context);
	}

	public void update(Person person) {
		Name dn = buildDn(person);
		DirContextOperations context = ldapTemplate.lookupContext(dn);
		mapToContext(person, context);
		ldapTemplate.modifyAttributes(context);
	}

	protected void mapToContext(Person person, DirContextOperations context) {
		context.setAttributeValues("objectclass", new String[] {"top", "person","inetOrgPerson","organizationalPerson"});
		context.setAttributeValue("cn", person.getFullname());
		context.setAttributeValue("sn", person.getLastname());
		context.setAttributeValue("description", person.getDescription());
		context.setAttributeValue("givenname",person.getGivenname());
		context.setAttributeValue("mail", person.getMail());
		//context.setAttributeValue("manager",person.getLastname());
		context.setAttributeValue("uid",person.getUid());
	}

	public void delete(Person person) {
		ldapTemplate.unbind(buildDn(person));
	}

	public Person findByPrimaryKey(String name) {
		Name dn = buildDn(name);
		return ldapTemplate.lookup(dn, contextMapper);
	}

	public List<Person> findByName(String name) {
		LdapQuery query = LdapQueryBuilder.query()
				.where("objectclass").is("person")
				.and("cn").whitespaceWildcardsLike(name);

		return ldapTemplate.search(query, contextMapper);
	}
    
	public List<Person> findAll() {
		EqualsFilter filter = new EqualsFilter("objectclass", "person");
		return ldapTemplate.search(LdapUtils.emptyLdapName(), filter.encode(), contextMapper);
	}
}
