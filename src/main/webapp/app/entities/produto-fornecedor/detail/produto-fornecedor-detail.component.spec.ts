import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProdutoFornecedorDetailComponent } from './produto-fornecedor-detail.component';

describe('ProdutoFornecedor Management Detail Component', () => {
  let comp: ProdutoFornecedorDetailComponent;
  let fixture: ComponentFixture<ProdutoFornecedorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProdutoFornecedorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ produtoFornecedor: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProdutoFornecedorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProdutoFornecedorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load produtoFornecedor on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.produtoFornecedor).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
