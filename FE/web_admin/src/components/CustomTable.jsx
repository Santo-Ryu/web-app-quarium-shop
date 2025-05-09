import DataTable from 'react-data-table-component';
import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export const CustomTable = ({
    buttonOption = null,
    hiddenSearchBar,
    columns,
    data,
    rowPerPage
}) => {
    const [search, setSearch] = useState("");

    const handleFilter = (event) => {
        setSearch(event.target.value);
    };

    const filteredData = data.filter((item) =>
        Object.values(item).some(
            value => typeof value === 'string' && value.toLowerCase().includes(search.toLowerCase())
        )
    );

    return (
        <section className="custom-table">
            {hiddenSearchBar && 
            <header className="custom-table__header">
                <input
                    className='custom-table__input'
                    type="text"
                    placeholder="Tìm kiếm"
                    value={search}
                    onChange={handleFilter}
                />
                {buttonOption != null && buttonOption.hidden ? 
                    <button className='custom-table__button' onClick={buttonOption.func}>
                        <FontAwesomeIcon icon={buttonOption.icon} />
                        {buttonOption.name}
                    </button> : ""
                }
            </header>
            }

            <article className='custom-table__content'>
                <DataTable
                    columns={columns}
                    data={filteredData}
                    responsive
                    highlightOnHover
                    striped
                    paginationPerPage={rowPerPage}
                    paginationRowsPerPageOptions={[]}
                    paginationComponentOptions={{
                        rowsPerPageText: '', // ẩn chữ "Rows per page"
                    }}
                    pagination={true}
                    style={{ cursor: 'pointer'}}
                />
            </article>
        </section>
    );
};
