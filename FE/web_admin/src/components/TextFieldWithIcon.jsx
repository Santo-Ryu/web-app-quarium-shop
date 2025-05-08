import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"

export const TextFieldWithIcon = ({ 
    icon, 
    placeholderText,
    value, 
    onChange,
    name
}) => {
    return (
        <>
            <div className="text-field">
                <FontAwesomeIcon className="text-field__icon" icon={icon} />
                <input 
                    className="text-field__input" 
                    type="text" 
                    placeholder={placeholderText}
                    value={value}
                    onChange={onChange}
                    name={name}
                />
            </div>
        </>
    )
}