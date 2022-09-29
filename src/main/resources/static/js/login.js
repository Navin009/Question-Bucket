const navToggle = document.getElementById("header-nav-toggle");
const nav = document.getElementById("header-nav");

navToggle.addEventListener("click", () => {
	if (navToggle.getAttribute("aria-expanded") === "false") {
		nav.setAttribute("aria-hidden", "false");
		navToggle.setAttribute("aria-expanded", "true");
	} else {
		nav.setAttribute("aria-hidden", "true");
		navToggle.setAttribute("aria-expanded", "false");
	}
});

const urlParams = new URLSearchParams(window.location.search);
if (urlParams.get("error") != null) {
	alert(urlParams.get("error"));
}
