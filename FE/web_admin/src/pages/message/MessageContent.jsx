import { faPaperPlane } from "@fortawesome/free-solid-svg-icons";
import { faUpload } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { MessageSidebar } from "./MessageSidebar";
import { MyMessage } from "./MyMessage";
import { FriendMessage } from "./FriendMessage";
import { useState } from "react";

export const MessageContent = () => {
    const listAccount = [
        { name: "Nguyễn Văn A", message: "Xin chào!", status: true },
        { name: "Trần Thị B", message: "Tôi cần hỗ trợ đơn hàng.", status: true },
        { name: "Lê Văn C", message: "Cảm ơn bạn nhiều." },
        { name: "Phạm Thị D", message: "Cho mình hỏi về sản phẩm mới.", status: true },
        { name: "Hoàng Văn E", message: "Khi nào có đợt giảm giá tiếp?gfhhhhhhhhhhhhhh", status: true },
        { name: "Đỗ Thị F", message: "Mình không đăng nhập được.", status: true },
        { name: "Ngô Văn G", message: "Bạn có thể gọi lại cho tôi không?", status: true },
        { name: "Vũ Thị H", message: "Đơn hàng của tôi bị chậm.", status: true },
        { name: "Bùi Văn I", message: "Cảm ơn bạn đã phản hồi nhanh.", status: true },
        { name: "Phan Thị J", message: "Mình sẽ quay lại sau.", status: true }  
    ];

    const [selectedAccount, setSelectedAccount] = useState(listAccount[0]);

    const handleSelectAccount = (account) => {
        setSelectedAccount(account);
    };

    return (
        <>
            <section className="message-content">
                {/* View chat */}
                <article className="message-content__view">
                    <header className="message-content__header">
                        <img className="message-content__image" src="/public/vite.svg" alt="Image" />
                        <div className="message-content__info">
                            <h4 className="message-content__name">{selectedAccount.name}</h4>
                            <p className="message-content__status">{selectedAccount.status ? 'Online' : 'Offline'}</p>
                        </div>
                    </header>

                    {/* Message */}
                    <div className="message-content__chat">
                        <FriendMessage 
                            selectedAccount={selectedAccount}
                        />
                        <MyMessage 
                            message={"Tôi có thể giúp gì cho bạn"}
                        />
                    </div>

                    <footer className="message-content__footer">
                        <div className="message-content__input"> 
                            <input 
                                className="message-content__input-text" 
                                type="text" 
                                placeholder="Tin nhắn"
                            />
                            <FontAwesomeIcon className="message-content__input-file" icon={faUpload} />
                        </div>
                        <FontAwesomeIcon className="message-content__send" icon={faPaperPlane} />
                    </footer>
                </article>

                {/* Sidebar */}
                <MessageSidebar 
                    listAccount={listAccount}
                    selectedAccount={selectedAccount}
                    onSelectAccount={handleSelectAccount}
                />
            </section>
        </>
    )
}