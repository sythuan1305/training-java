//--- upload ---
// upload file to imgbb.com
/**
 * tải ảnh lên imgbb.com
 * @param file ảnh cần tải lên
 * @deprecated Không còn dùng nữa
 */
function uploadFile(file) {
    let formData = new FormData();
    formData.append("image", file);

    $.ajax({
        url: "https://api.imgbb.com/1/upload?key=0a2f694b33e70dc6d06249c85fa8bb63",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            console.log(data.data.url);
            let img_url = document.getElementById("image_url");
            img_url.value = data.data.url;
            reloadSubmitBtnByForm();
        },
        error: function (data) {
            console.log("error");
            console.log(data);
        }
    });
}


/**
 * Xác định xem có thể bật nút submit hay không <br>
 * Nếu đầy đủ thông tin thì bật nút submit và ngược lại
 */
function reloadSubmitBtnByUploadForm() {
    let btnSubmit = document.getElementById("btn-upload-product");
    let name = document.forms["form-upload-product"]["name"].value;
    let price = document.forms["form-upload-product"]["price"].value;
    let quantity = document.forms["form-upload-product"]["quantity"].value;
    btnSubmit.disabled = name.length === 0 || price.length === 0 || quantity.length === 0 || currentFiles.length === 0;
}

// region upload image
// những file ảnh hiện tại
let currentFiles = [];

/**
 * Xử lý khi thay đổi input file ảnh
 * @param e sự kiện change
 */
function handleChangeInputImage(e) {
    // lấy ra container chứa ảnh
    let imgContainer = document.querySelector('.img-container');
    // xóa hết ảnh cũ
    imgContainer.innerHTML = '';

    // lấy ra các file ảnh mới
    let files = e.target.files;
    // và gán lại cho biến currentFiles
    currentFiles = files;

    // duyệt qua từng file ảnh
    for (let i = 0; i < files.length; i++) {
        let file = files[i];

        // Tạo wrapper chứa ảnh và nút xóa
        let imgWrapper = document.createElement('div');
        imgWrapper.classList.add('img-wrapper');

        // Tạo image tag và gán ảnh vào
        // var img = document.createElement('img');
        // img.src = URL.createObjectURL(file);
        // img.alt = file.name;

        // Tạo text tag và gán tên file vào
        let text = document.createElement('p');
        text.textContent = file.name;

        // Tạo nút xóa và gán sự kiện xóa vào
        let deleteBtn = document.createElement('button');
        deleteBtn.classList.add('delete-btn');
        deleteBtn.textContent = 'Delete';
        deleteBtn.addEventListener('click', function (event) {
            // Xóa wrapper chứa ảnh
            let imgWrapper = event.currentTarget.parentElement;
            imgWrapper.remove();

            // Xóa file khỏi currentFiles
            currentFiles = Array.from(currentFiles).filter(function (file) {
                console.log(imgWrapper.querySelector("p").innerText);
                return file.name !== imgWrapper.querySelector("p").innerText;
            });

            // reload lại nút submit
            reloadSubmitBtnByUploadForm();
        });


        // Thêm ảnh và text vào wrapper
        // imgWrapper.appendChild(img);
        imgWrapper.appendChild(text);
        imgWrapper.appendChild(deleteBtn);

        // Thêm img wrapper vào container
        imgContainer.appendChild(imgWrapper);
    }
}

/**
 * Xử lý khi nhấn nút submit
 */
function handleOnSubmit() {
    let formData = new FormData();

    // Thêm các trường input vào FormData
    formData.append("name", document.getElementById("name").value);
    formData.append("price", document.getElementById("price").value);
    formData.append("quantity", document.getElementById("quantity").value);

    // Thêm các file từ fileList vào FormData
    for (let i = 0; i < currentFiles.length; i++) {
        formData.append("files", currentFiles[i]);
    }

    // Gửi đối tượng FormData lên server
    let xhr = new XMLHttpRequest();
    // xhr.open("post", "/admin/product/upload", true);
    xhr.send(formData);
}

// endregion
