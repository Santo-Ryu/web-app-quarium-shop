import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"

export const DetailsContent = ({
    listButton,
    layout
}) => {
    return (
        <>
            <main className="customer-details">
                <section className="customer-details__info">
                    <h4 className="customer-details__header">Thông tin</h4>
                    {/* LAYOUT */}
                    {layout}
                </section>

                <article className="customer-details__func">
                    <h3 className="customer-details__func--header">Hình ảnh</h3>
                    <img className="customer-details__func--image" src="/src/assets/beta1.jpg" alt="Image" />
                    <ul className="customer-details__func--list-button">
                        {listButton.map((e, key) => (
                            <li className="list-button__item" key={key}>
                                <button onClick={e.onClick}>
                                    <FontAwesomeIcon icon={e.icon} />
                                    <p>{e.button}</p>
                                </button>
                            </li>
                        ))}
                    </ul>
                </article>
            </main>
        </>
    )
}