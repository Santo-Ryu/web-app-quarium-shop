export const UserCard = ({
    userObject,
    selectedAccount,
    onSelect
}) => {
    const isSelected = selectedAccount && selectedAccount.name === userObject.name;

    return (
        <>
            <article 
                className={`user-card ${isSelected ? 'select' : ''}`}
                onClick={onSelect}
            >
                <img className="user-card__image" src="/public/vite.svg" alt="Image" />
                <div className="user-card__content">
                    <h4 className="user-card__name">{userObject.name}</h4>
                    <p className="user-card__message">{userObject.message}</p>
                </div>
            </article>
        </>
    )
}