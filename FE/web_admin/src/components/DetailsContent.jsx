import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { BASE_URL } from "../app/config/configApi"
import { faClose } from "@fortawesome/free-solid-svg-icons"

export const DetailsContent = ({
    listButton,
    layout,
    image,
    images = [],
    onImageClick,
    fileInputRef,
    onImageChange,
    onDeleteImage = null,
    hiddenImages = false
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

                    {image && 
                        <img 
                            className="details-content__func--image"
                            src={`${BASE_URL}api/public/image?name=${image}`} 
                            alt="Image"    
                            onClick={onImageClick}
                        />
                    }
                        
                    {/* Hidden file input */}
                    <input
                        type="file"
                        accept="image/*"
                        ref={fileInputRef}
                        onChange={onImageChange}
                        style={{ display: "none" }}
                    />

                    {hiddenImages &&
                        <div className="details-content__func--images">
                            <div className="details-content__func--image-list">
                                {images?.map((e, i) => (
                                    <div key={i} className="details-content__func--image-item">
                                        <img 
                                            className="details-content__func--image-ls"
                                            src={e.preview} 
                                            alt="Image"    
                                        />
                                        <button onClick={() => onDeleteImage?.(e)}>
                                            Xóa
                                        </button>
                                    </div>
                                ))}
                            </div>
                            <input 
                                className="details-content__func--choose-images" 
                                ref={fileInputRef} 
                                onChange={onImageChange}
                                type="file" 
                            />
                        </div>
                    }

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