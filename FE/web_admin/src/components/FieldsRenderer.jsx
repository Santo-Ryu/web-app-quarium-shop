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

export const FieldsRenderer = ({fields, onChange}) => {
    const typeGender = (type) => {
        switch(type) {
            case "OTHER": return "Khác"
            case "FEMALE": return "Nữ"
            case "MALE": return "Nam"
        }
    }
    return (
        <>
            <dl className="fields-renderer">
                {fields.map((field, idx) => (
                    <div className="fields-renderer__item" key={idx}>
                        {field.label && <label className="fields-renderer__item--label">{field.label}</label>}
                        {field.type === "quill" ? (
                            <ReactQuill 
                                className="fields-renderer__quill" theme="snow" 
                                value={field.value} 
                                modules={modules} 
                                readOnly={field.disabled} 
                                onChange={(value) => onChange(field.key, value)}
                            />
                        ) : field.type === "select" ? (
                            <select 
                                className="fields-renderer__item--select" 
                                disabled={field.disabled}
                                value={field.value}
                                onChange={(e) => onChange(field.key, e.target.value)}
                            >
                            {field?.options.map((opt, i) => (
                                <option key={i} value={opt}>{typeGender(opt)}</option>
                            ))}
                            </select>
                        ) : field.type === "button" ? (
                            <div className="fields-renderer__item--button">
                                {field.buttons.map((e, key) => (
                                    <button className={e.className} key={key} onClick={e.onClick}>{e.text}</button>
                                ))}
                            </div>
                        ) : (
                            <input
                                className="fields-renderer__item--input"
                                type={field?.type}
                                value={field.value}
                                disabled={field?.disabled}
                                onChange={(e) => onChange(field.key, e.target.value)}
                            />
                        )}
                    </div>
                ))}
            </dl>
        </>
    )
}