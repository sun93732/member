package nz.paymark.member.web.controller;

import static nz.paymark.shared.constant.Mime.MIME_JSON;
import static nz.paymark.shared.validation.util.ExceptionThrower.throwBadRequestIf;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import nz.paymark.member.api.MemberService;
import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;
import nz.paymark.service.shared.controller.AbstractRestController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping(value = "/")
public class MemberServiceController extends AbstractRestController {
	private static final String ROLE_CREATE = "ROLE_createMember";
	private static final String ROLE_READ = "ROLE_readMember";
	private static final String ROLE_UPDATE = "ROLE_updateMember";
	private static final String ROLE_DELETE = "ROLE_deleteMember";
	private static final String ROLE_SEARCH = "ROLE_searchMember";

	@Value("${member.service.member.get}")
	private String selfGetUrl;

	@Resource
	private MemberService service;

	@ApiOperation(value = "Retrieve details for a specific member, by id.", notes = "returns the member, or a 404 if the member doesn't exist.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "member not found") })
	@Secured(ROLE_READ)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MIME_JSON)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Member> handleRetrieve(HttpServletRequest request,
			HttpServletResponse response, Principal principal,
			@PathVariable String id) {
		Optional<Member> result = service.findMemberById(id);
		
		if(result.isPresent()) {
		    Member member = result.get();
		    addSelfLink(member, selfGetUrl);
			return new ResponseEntity<Member>(member, HttpStatus.OK);	
		}		

		return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Create a new member.", notes = "No member id is permitted in the request. Returns the created member, including id and status.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Member ceated"),
			@ApiResponse(code = 400, message = "Submitted member is not in a valid format") })
	@Secured(ROLE_CREATE)
	@RequestMapping(method = RequestMethod.POST, consumes = MIME_JSON, produces = MIME_JSON)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Member> handleCreate(HttpServletRequest request,
			HttpServletResponse response, Principal principal,
			@RequestBody Member member) {

		Member newMember = service.createMember(member);
		addSelfLink(newMember, selfGetUrl);
		return new ResponseEntity<Member>(newMember, HttpStatus.OK);
	}

	@ApiOperation(value = "Search for member by the provided criteria.", notes = "returns a list of member matching the provided criteria, or a 404 if the criteria matches nothing.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "No members match criteria") })
	@Secured(ROLE_SEARCH)
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MIME_JSON)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Member>> handleSearch(
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute MemberSearchCriteria crit) {
		List<Member> members = service.searchMember(crit.getRole(),
				crit.getStatus(), crit.getOrganisationId(), crit.getUserId());

		for (Member member : members) {
			addSelfLink(member, selfGetUrl);
		}
		if (!members.isEmpty()) {
			return new ResponseEntity<List<Member>>(members, HttpStatus.OK);
		}

		return new ResponseEntity<List<Member>>(Collections.emptyList(),
				HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Update an existing member.", notes = "Returns the updated member.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Submitted member is not in a valid format"),
			@ApiResponse(code = 404, message = "Member not found") })
	@Secured(ROLE_UPDATE)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MIME_JSON, produces = MIME_JSON)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Member> handleUpdate(@PathVariable String id,
			@RequestBody @Valid Member member) {
		throwBadRequestIf(!id.equals(member.getId()), "id",
				"Both ids must be the same");
		if (!service.findMemberById(id).isPresent()) {
			return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
		}
		;
		member = service.updateMember(member);
		addSelfLink(member, selfGetUrl);
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete an existing member.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Member not found") })
	@Secured(ROLE_DELETE)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = MIME_JSON, produces = MIME_JSON)
	@ResponseStatus(HttpStatus.GONE)
	public ResponseEntity<String> handleDelete(@PathVariable String id) {
		if (!service.findMemberById(id).isPresent()) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		
		service.deleteMember(id);
		return new ResponseEntity<String>(HttpStatus.GONE);
	}
}
