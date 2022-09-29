const searchInput = document.getElementById("search-input");

searchInput?.addEventListener("keyup", function (event) {
	event.preventDefault();
	if (event.code === "Enter") {
		searchText();
	}
});

function searchText() {
	if (searchInput.value.length > 0 && searchInput.value != " ") {
		location.href = "/search?q=" + searchInput.value;
	}
}

if (location.pathname === "/search" && searchInput != null) {
	url = new URLSearchParams(location.search);
	searchInput.value = url.get("q");
}

function questionRedirect(id) {
	location.href = "/question/" + id;
}
