
export const FriendMessage = ({
    selectedAccount
}) => {
    return (
        <>
            <section className="message-send by-customer">
                <img className="message-send__image" src="/public/vite.svg" alt="Image" />
                <div className="message-send__box">
                    <p className="message-send__text">{selectedAccount.message}</p>
                    <p className="message-send__time message-send__by-customer">10:30</p>
                </div>
            </section>
        </>   
    )
}