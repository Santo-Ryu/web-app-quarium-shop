import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";

const modules = {
  toolbar: [
    [{ 'header': [1, 2, 3, false] }],
    ['bold', 'italic', 'underline', 'strike'],
    [{ 'color': [] }, { 'background': [] }],
    [{ 'list': 'ordered' }, { 'list': 'bullet' }],
    [{ 'align': [] }],
    ['link', 'image', 'video'],
    ['clean']
  ]
};

export const ProductDetailsContent = ({
    product,
    disabledInput
}) => {
    const fields = [
    { label: "ID sản phẩm", type: "text", name: "id" , disabled: disabledInput},
    { label: "Danh mục", type: "select", name: "category", options: ["Cá cảnh", "Cây thủy sinh"], disabled: disabledInput},
    { label: "Tên sản phẩm", type: "text", name: "name", disabled: disabledInput },
    { label: "Mô tả", type: "quill", name: "description", disabled: disabledInput },
    { label: "Đánh giá", type: "text", name: "rating", disabled: disabledInput },
    { label: "Giá tiền", type: "number", name: "price", disabled: disabledInput },
    { label: "Tồn kho", type: "number", name: "stock", disabled: disabledInput },
    { label: "Đã bán", type: "number", name: "sold", disabled: disabledInput },
    { label: "Ngày thêm vào", type: "date", name: "createdAt", disabled: disabledInput },
    { label: "Cập nhật gần đây", type: "date", name: "updatedAt", disabled: disabledInput },
    ];

    return (
        <>
            <dl className="product-details">
                {fields.map((field, idx) => (
                    <div className="product-details__item" key={idx}>
                        <label className="product-details__item--label">{field.label}</label>
                        {field.type === "quill" ? (
                            <ReactQuill className="product-quill" theme="snow" modules={modules} readOnly={disabledInput} />
                        ) : field.type === "select" ? (
                            <select className="product-details__item--select" disabled={disabledInput}>
                            {field.options.map((opt, i) => (
                                <option key={i} value={opt}>{opt}</option>
                            ))}
                            </select>
                        ) : (
                            <input
                                className="product-details__item--input"
                                type={field.type}
                                value={product?.[field.name] || ""}
                                disabled={disabledInput}
                            />
                        )}
                    </div>
                ))}
            </dl>
        </>
    )
}