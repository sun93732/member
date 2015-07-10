package nz.paymark.member.web.controller;

import static nz.paymark.client.shared.web.exception.WebExceptionThrower.throwBadRequestIf;
import static nz.paymark.shared.constant.Mime.MIME_JSON;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import nz.paymark.member.api.MemberService;
import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;
import nz.paymark.member.model.enumerator.MemberRole;
import nz.paymark.service.shared.controller.AbstractRestController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	public Member handleRetrieve(@PathVariable String id) {
		Member member = service.getMember(id);
		addSelfLink(member, selfGetUrl);
		return member;	
	}

	@ApiOperation(value = "Create a new member.", notes = "No member id is permitted in the request. Returns the created member, including id and status.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Member ceated"),
			@ApiResponse(code = 400, message = "Submitted member is not in a valid format") })
	@Secured(ROLE_CREATE)
	@RequestMapping(method = RequestMethod.POST, consumes = MIME_JSON, produces = MIME_JSON)
	@ResponseStatus(HttpStatus.CREATED)
	public Member handleCreate(HttpServletRequest request,
			HttpServletResponse response, Principal principal,
			@RequestBody Member member) {
		Member memberCreated = service.createMember(member);
		addSelfLink(memberCreated, selfGetUrl);
		return memberCreated;
	}

	@ApiOperation(value = "Search for member by the provided criteria.", notes = "returns a list of member matching the provided criteria, or a 404 if the criteria matches nothing.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "No members match criteria") })
	@Secured(ROLE_READ)
	@RequestMapping(value = "/query", method = RequestMethod.GET, produces = MIME_JSON)
	@ResponseStatus(HttpStatus.OK)
	public List<Member> handleSearch(@RequestParam(required = false) List<String> ids) {
		List<Member> memberList = service.searchMembers(ids);
        for (Member member : memberList) {
            addSelfLink(member, selfGetUrl);
        }
        return memberList;
	}
	
	@ApiOperation(value = "Search for Member by the provided criteria.", notes = "returns a list of Member matching the provided criteria, or a 404 if the criteria matches nothing.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "No members match criteria") })
    @Secured(ROLE_READ)
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MIME_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<Member> handleSearchCriteria(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String organisationId,
            @RequestParam(required = false) MemberRole role,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime creationTimeBegin,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime creationTimeEnd,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime modificationTimeBegin,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime modificationTimeEnd) {
        MemberSearchCriteria criteria = new MemberSearchCriteria(
                userId, organisationId, role, creationTimeBegin, creationTimeEnd,
                modificationTimeBegin, modificationTimeEnd);
        List<Member> members = service
                .searchMembersByCriteria(criteria);

        for (Member member : members) {
            addSelfLink(member, selfGetUrl);
        }

        return members;
    }

	@ApiOperation(value = "Update an existing member.", notes = "Returns the updated member.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Submitted member is not in a valid format"),
			@ApiResponse(code = 404, message = "Member not found") })
	@Secured(ROLE_UPDATE)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MIME_JSON, produces = MIME_JSON)
	@ResponseStatus(HttpStatus.OK)
	public Member handleUpdate(@PathVariable String id,
			@RequestBody @Valid Member member) {
		throwBadRequestIf(!id.equals(member.getId()), "id",
				"Both ids must be the same");
		member = service.updateMember(member);
        addSelfLink(member, selfGetUrl);
        return member;
	}

	@ApiOperation(value = "Delete an existing member.")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Deleted Member"),
			@ApiResponse(code = 404, message = "Member not found") })
	@Secured(ROLE_DELETE)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = MIME_JSON, produces = MIME_JSON)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void handleDelete(@PathVariable String id) {
		service.deleteMember(id);
	}
}
