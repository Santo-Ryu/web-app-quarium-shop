import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"

export const DashboardCard = ({
    fontAweSome,
    title,
    content,
    details
}) => {

    return (
        <>
            <div className="dashboard-card">
                <div className='dashboard-card__title'>
                    <FontAwesomeIcon icon={fontAweSome} />
                    <div className="dashboard-card__content">
                        <h4>{title}</h4> 
                        <p>{content}</p>
                    </div>
                </div>
                <hr className="dashboard-card__line" />
                <div className="dashboard-card__details">
                    <span 
                        style={{color: details.number > 0 ? 'green' : 'red'}}
                    >{details.number}</span>&nbsp;
                    <span>{details.text}</span>
                </div>
            </div>
        </>
    )
}