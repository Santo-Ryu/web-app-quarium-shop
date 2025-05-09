import DataTable from 'react-data-table-component';
import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export const CustomTable = ({
    selectOption = null,
    buttons = null,
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
            <header className="custom-table__header">
                {selectOption != null && 
                    <select className='custom-table__select' name="" id="">
                        {selectOption.map((e, key) => (
                            <option key={key} value={e.id}>{e.option}</option>
                        ))}
                    </select>
                }
                {hiddenSearchBar && 
                    <input
                        className='custom-table__input'
                        type="text"
                        placeholder="Tìm kiếm"
                        value={search}
                        onChange={handleFilter}
                    />
                }
                {buttons != null && 
                    <div className='custom-table__buttons'>
                        {buttons != null && buttons.map((e, key) => (
                            <button className='custom-table__buttons--item' onClick={e.func} key={key}>
                                <FontAwesomeIcon icon={e.icon} />
                                {e.name}
                            </button>
                        ))}
                    </div>
                }
            </header>

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
