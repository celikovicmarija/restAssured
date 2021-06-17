package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Permissions {
    private Boolean admin;
    private Boolean push;
    private Boolean pull;
}
