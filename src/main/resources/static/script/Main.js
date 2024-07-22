function toggleInformation() {
    var infoPanel = document.querySelector('.information');
    if (infoPanel.style.display === 'none' || infoPanel.style.display === '') {
        infoPanel.style.display = 'block'; /* Hiển thị phần tử */
    } else {
        infoPanel.style.display = 'none'; /* Ẩn phần tử */
    }
}
