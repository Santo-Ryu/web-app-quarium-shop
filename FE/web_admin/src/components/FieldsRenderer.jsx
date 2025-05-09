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

export const FieldsRenderer = ({fields}) => {
    return (
        <>
            <dl className="fields-renderer">
                {fields.map((field, idx) => (
                    <div className="fields-renderer__item" key={idx}>
                        <label className="fields-renderer__item--label">{field.label}</label>
                        {field.type === "quill" ? (
                            <ReactQuill className="fields-renderer__quill" theme="snow" modules={modules} readOnly={field.disabled} />
                        ) : field.type === "select" ? (
                            <select className="fields-renderer__item--select" disabled={field.disabled}>
                            {field.options.map((opt, i) => (
                                <option key={i} value={opt}>{opt}</option>
                            ))}
                            </select>
                        ) : (
                            <input
                                className="fields-renderer__item--input"
                                type={field.type}
                                value={""}
                                disabled={field.disabled}
                            />
                        )}
                    </div>
                ))}
            </dl>
        </>
    )
}