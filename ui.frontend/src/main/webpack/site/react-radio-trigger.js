document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".requiresDecoration").forEach((el) => {
        window.decorate(el)
    });
});
