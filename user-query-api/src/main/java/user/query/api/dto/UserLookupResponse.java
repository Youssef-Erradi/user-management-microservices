package user.query.api.dto;

import java.util.List;

import user.core.models.User;

public record UserLookupResponse(List<User> users) {

}
