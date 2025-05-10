import { Helmet } from "react-helmet-async";
import { MainLayout } from "../../layouts/MainLayout";
import { faCheck, faMessage, faTags, faTimes } from "@fortawesome/free-solid-svg-icons";
import { CustomTable } from "../../components/CustomTable";

const columns = [
    {
        name: <span className="columns__header" style={{ fontSize: "0.9rem" }}> Chọn </span>,
        sortable: true,
        width: "100px",
        cell: (row) => (
            <div>
                <input  type="checkbox"
                />
            </div>
        ),
    },
    {
        name: <span className="column__header">Hình ảnh</span>,
        sortable: true,
        width: '100px',
        cell: row => (<img src={row.image} style={{borderRadius: '8px', border: '1px solid gray', width: '40px', height: '40px', objectFit: 'cover'}} alt='Product Image' />)
    },
    {
        name: <span className="column__header" style={{ fontSize: "0.9rem" }}> Sản phẩm </span>,
        sortable: true,
        selector: (row) => row.name,
    },
    {
        name: <span className="column__header" style={{ fontSize: "0.9rem" }}> Khuyến mãi (%) </span>,
        sortable: true,
        cell: (row) => (
            <input
                className="column__input"
                type="text" value={row.discount}
            />
        ),
        width: "200px"
    },
    {
        name: <span className="column__header" style={{ fontSize: "0.9rem" }}> Hết hạn </span>,
        sortable: true,
        cell: (row) => (
            <input
                className="column__input"
                type="date" value={row.expiredAt}
            />
        ),
        width: "200px"
    }
]

const data = [
  {
    id: 1,
    name: "Cá beta sữa",
    image: '/src/assets/beta3.jpg',
    discount: "10",
    expiredAt: "2025-06-30",
  },
  {
    id: 2,
    name: "Cá beta siêu đẹp",
    image: '/src/assets/beta1.jpg',
    discount: "15",
    expiredAt: "2025-07-15",
  }
];


export const Discount = () => {
    const label = {text: 'Giảm giá', icon: faTags}

    const buttons = [
        {func: () => {}, name: "Hủy khuyến mãi", icon: faTimes},
        {func: () => {}, name: "Áp dụng khuyến mãi", icon: faCheck},
    ]

    return (
        <>
            <Helmet><title>{label.text}</title></Helmet>
            <MainLayout 
                label={label}
                layout={
                    <CustomTable 
                        buttons={buttons}
                        columns={columns}
                        data={data}
                        rowPerPage={10}
                    />
                }
            />
        </>
    )
}