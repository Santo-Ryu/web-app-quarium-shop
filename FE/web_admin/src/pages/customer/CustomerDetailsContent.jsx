import { faClose, faGear, faHistory, faSave } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { useState } from "react"
import { useNavigate } from "react-router-dom"

export const CustomerDetailsContent = ({
    typeInfo
}) => {

    return (
        <>
            <ul className="customer-details__list">
                {typeInfo.map((e, key) => (
                    <li className="customer-details__list--item"  key={key}>
                        <label htmlFor="">{e.label}</label>
                        <input className={e.disabled ? `hidden-outline` : ''} type={e.inputType} value={e.value} disabled={e.disabled} />
                    </li>
                ))}
            </ul>
        </>
    )
}