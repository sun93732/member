package nz.paymark.member.web.controller;

import static nz.paymark.shared.constant.Mime.MIME_JSON;

import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import nz.paymark.member.api.MemberService;
import nz.paymark.member.model.Member;
import nz.paymark.service.shared.controller.AbstractRestController;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/")
public class MemberServiceController extends AbstractRestController{
	private static final String ROLE_CREATE = "ROLE_createMember";
	private static final String ROLE_READ = "ROLE_readMember";
	private static final String ROLE_UPDATE = "ROLE_updateMember";
	
	@Value("${member.service.member.get}")
	private String selfGetUrl;
	
	@Resource
	private MemberService service;
	
	@ApiOperation(value="Retrieve foo.")
	@ApiResponses(value={
			@ApiResponse(code=200, message="OK"),
			@ApiResponse(code=404, message="Contract not found") })
	@RequestMapping(value="/foo", method=RequestMethod.GET, produces=MIME_JSON)
	@ResponseStatus(HttpStatus.OK)
	public String handleFoo(HttpServletRequest request, Principal principal) {
		
		return "foo bar";
	}
	
	@ApiOperation(value="Retrieve details for a specific member, by id.", notes="returns the contract, or a 404 if the member doesn't exist.")
	@ApiResponses(value={
			@ApiResponse(code=200, message="OK"),
			@ApiResponse(code=404, message="Contract not found") })
	@Secured(ROLE_READ)
	@RequestMapping(value="/{id}", method=RequestMethod.GET, produces=MIME_JSON)
	@ResponseStatus(HttpStatus.OK)
	public Member handleRetrieve(HttpServletRequest request, Principal principal, @PathVariable String id) {
		Member contract = service.getMember(id);
		addSelfLink(contract, selfGetUrl);
		return contract;
	}

	
	
	@ApiOperation(value="Create a new member.", notes="No member id is permitted in the request. Returns the created member, including id and status.")
	@ApiResponses(value={
			@ApiResponse(code=201, message="CREATED"),
			@ApiResponse(code=400, message="Submitted contract is not in a valid format") })
	@Secured(ROLE_CREATE)
	@RequestMapping(method=RequestMethod.POST, consumes=MIME_JSON, produces=MIME_JSON)
	@ResponseStatus(HttpStatus.CREATED)
	public Member handleCreate(HttpServletRequest request, Principal principal, @RequestBody Member member) {
		Member result = service.createMember(member);
		addSelfLink(result, selfGetUrl);
		return result;
	}
}
