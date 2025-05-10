import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"

export const DetailsContent = ({
    listButton,
    layout
}) => {
    return (
        <>
            <main className="details-content">
                <section className="details-content__info">
                    <h4 className="details-content__header">Thông tin</h4>
                    {/* LAYOUT */}
                    {layout}
                </section>

                <article className="details-content__func">
                    <h3 className="details-content__func--header">Hình ảnh</h3>
                    <img className="details-content__func--image" src="/src/assets/avt1.jpg" alt="Image" />
                    <ul className="details-content__func--list-button">
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