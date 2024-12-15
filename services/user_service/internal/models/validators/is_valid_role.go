package validators

var roles = []string{"STUDENT", "TEACHER", "ADMIN"}

func IsValidRoleEnum(item string) bool {
	for _, element := range roles {
		if element == item {
			return true
		}
	}
	return false
}
