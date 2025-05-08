import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { UserCard } from "./UserCard";

export const MessageSidebar = ({
    listAccount,
    selectedAccount,
    onSelectAccount
}) => {

    return (
        <>
            <aside className="message-sidebar">
                <h3 className="message-sidebar__label">Đoạn chat</h3>
                <div className="message-sidebar__search-bar">
                    <input type="text" placeholder="Tìm kiếm" />
                    <FontAwesomeIcon icon={faMagnifyingGlass} />
                </div>
                <div className="message-sidebar__list-account">
                    {listAccount.map((e, index) => (
                        <UserCard 
                            key={index}
                            userObject={e}
                            selectedAccount={selectedAccount}
                            onSelect={() => onSelectAccount(e)}
                        />
                    ))}
                </div>
            </aside>
        </>
    )
}